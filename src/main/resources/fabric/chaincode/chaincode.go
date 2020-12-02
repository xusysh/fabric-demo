package main

import (
	"bytes"
	"container/list"
	"encoding/json"
	"fmt"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	sc "github.com/hyperledger/fabric/protos/peer"
	"github.com/satori/go.uuid"
	"strconv"
	"time"
)

type SmartContract struct {
}

// 账户信息 key为userid
type Account struct {
	Id       string  `json:"id"`
	UserId   string  `json:"userid"`
	Password string  `json:"password"`
	Name     string  `json:"name"`
	Balance  float64 `json:"balance"`
	OrgId    string  `json:"orgId"`
}

// 交易记录 key为id
type Record struct {
	Id          string  `json:"id"`
	Date        string  `json:"date"`
	Time        string  `json:"time"`
	From        string  `json:"from"`
	To          string  `json:"to"`
	Money       float64 `json:"money"`
	PurseId     string  `json:"purseId"`
	Timestamp   int64   `json:"timestamp"`
	FromBalance float64 `json:"frombalance"`
	ToBalance   float64 `json:"tobalance"`
}

// 收支情况
type IncomeExpense struct {
	Date         string `json:"date"`
	Income       string `json:"income"`
	Expense      string `json:"expense"`
	TotalBalance string `json:"totalbalance"`
}

// 钱包 key为ID
type Purse struct {
	Id       string  `json:"id"`
	Balance  float64 `json:"money"`
	UserId   string  `json:"userId"`
	ParentId string  `json:"parentId"`
	Remark   string  `json:"remark"`
}

// 资金流向
type Flow struct {
	TargetId string  `json:"targetId"`
	Amount   float64 `json:"amount"`
}

/*
 *  初始化用户信息
 */
func (s *SmartContract) Init(APIstub shim.ChaincodeStubInterface) sc.Response {
	Accounts := []Account{
		Account{Id: "acc1", UserId: "zhuhao2.js", Password: "123456", Name: "朱浩", Balance: 100},
		Account{Id: "acc2", UserId: "guojingyu.js", Password: "123456", Name: "郭靖宇", Balance: 100},
		Account{Id: "acc3", UserId: "shimingjie.js", Password: "123456", Name: "施铭杰", Balance: 100},
		Account{Id: "acc4", UserId: "gonghui.js", Password: "123456", Name: "工会", Balance: 0},
	}
	Purses := []Purse{
		Purse{Id: "purse1", Balance: 100, UserId: "zhuhao2.js", ParentId: "", Remark: ""},
		Purse{Id: "purse2", Balance: 100, UserId: "guojingyu.js", ParentId: "", Remark: ""},
		Purse{Id: "purse3", Balance: 100, UserId: "shimingjie.js", ParentId: "", Remark: ""},
		Purse{Id: "purse4", Balance: 0, UserId: "gonghui.js", ParentId: "", Remark: ""},
	}
	i := 0
	for i < len(Accounts) {
		accountAsBytes, _ := json.Marshal(Accounts[i])
		APIstub.PutState(Accounts[i].UserId, accountAsBytes)
		purseAsBytes, _ := json.Marshal(Purses[i])
		//		uuid, _ := GetUUID()
		APIstub.PutState("purse"+string(i+1), purseAsBytes)
		i = i + 1
	}
	return shim.Success(nil)
}

/*
 *  调用入口，根据输入参数选择调用对应的方法，当前包含：donate（捐赠），wallet（查看用户信息），record（交易记录）, inAndOut(资金收支情况)
 */
func (s *SmartContract) Invoke(APIstub shim.ChaincodeStubInterface) sc.Response {

	function, args := APIstub.GetFunctionAndParameters()
	if function == "donate" {
		return s.donate(APIstub, args)
	} else if function == "wallet" {
		return s.wallet(APIstub, args)
	} else if function == "record" {
		return s.record(APIstub, args)
	} else if function == "inAndOut" {
		return s.recordByDate(APIstub, args)
	}

	return shim.Error("Invalid Smart Contract function name.")
}

/*
 *  donate方法，即捐赠方法，需要指定转出方(args[0])、转出方(args[1])和金额(args[2])
 */
func (s *SmartContract) donate(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	if len(args) != 3 {
		return shim.Error("Donate Money Failed: Incorrect number of arguments. Expecting 3")
	}

	// 1.判断转出方用户是否存在
	accountFrom, errFrom := APIstub.GetState(args[0])
	if errFrom != nil {
		fmt.Println("Donate Money Failed:" + errFrom.Error())
		return shim.Error("Donate Money Failed:" + errFrom.Error())
	}
	accountFromJson := Account{}
	json.Unmarshal(accountFrom, &accountFromJson)

	// 2.判断转出金额是否小于转出方所有存款
	money, _ := strconv.ParseFloat(args[2], 64)
	if accountFromJson.Balance < money {
		fmt.Println("Donate Money Failed: no enough balance")
		return shim.Error("Donate Money Failed: no enough balance")
	}

	// 3.判断转入方用户是否存在
	accountTo, errTo := APIstub.GetState(args[1])
	if errTo != nil {
		fmt.Println("Donate Money Failed:" + errTo.Error())
		return shim.Error("Donate Money Failed:" + errTo.Error())
	}
	accountToJson := Account{}
	json.Unmarshal(accountTo, &accountToJson)

	// 4.执行转账交易
	accountFromJson.Balance -= money
	accountToJson.Balance += money
	accountFrom, _ = json.Marshal(accountFromJson)
	accountTo, _ = json.Marshal(accountToJson)
	// 4.1 转出方account信息更新
	fromUpdateErr := APIstub.PutState(args[0], accountFrom)
	if fromUpdateErr != nil {
		fmt.Println("Donate Money Failed" + fromUpdateErr.Error())
		return shim.Error(fromUpdateErr.Error())
	}
	// 4.2 转入方account信息更新
	toUpdateErr := APIstub.PutState(args[1], accountTo)
	if toUpdateErr != nil {
		// 4.2.1 如果发生故障，进行回退
		accountFromJson.Balance += money
		accountFrom, _ = json.Marshal(accountFromJson)
		APIstub.PutState(args[0], accountFrom)
		fmt.Println("Donate Money Failed" + toUpdateErr.Error())
		return shim.Error(fromUpdateErr.Error())
	}
	// 4.3 转出方purse信息更新
	queryString := fmt.Sprintf(`{"selector": {"userId": %v}, "parentId": {"$regex" : "*"}}`, args[0])
	purseAsBytes, purseErr := APIstub.GetQueryResult(queryString)
	if purseErr != nil {
		fmt.Println("Donate Money Failed:" + purseErr.Error())
		return shim.Error(purseErr.Error())
	}
	defer purseAsBytes.Close()
	i := money
	remark, _ := getUUID()
	for purseAsBytes.HasNext() {
		purseRes, err := purseAsBytes.Next()
		if err != nil {
			return shim.Error("Donate Money Failed:" + err.Error())
		}
		purseTemp := Purse{}
		json.Unmarshal(purseRes.Value, &purseTemp)
		if purseTemp.Balance-i >= 0 {
			purseTemp.Balance = purseTemp.Balance - i
			i = 0
		} else {
			purseTemp.Balance = 0
			i = i - purseTemp.Balance
		}
		purseTemp.Remark = remark
		purseRes.Value, _ = json.Marshal(purseTemp)
		APIstub.PutState(purseRes.Key, purseRes.Value)
		// 4.4 转入方新增一条purse
		purse := Purse{}
		purse.Id, _ = getUUID()
		purse.UserId = args[1]
		purse.Balance = money
		purse.ParentId = purseTemp.Id
		purse.Remark = remark
		purseNew, _ := json.Marshal(purse)
		APIstub.PutState(purse.Id, purseNew)
		// 4.5 增加交易记录
		var argsnew []string
		argsnew[0] = args[0]
		argsnew[1] = args[1]
		argsnew[2] = args[2]
		argsnew[3] = purse.Id
		argsnew[4] = strconv.FormatFloat(accountFromJson.Balance, 'E', -1, 64)
		argsnew[5] = strconv.FormatFloat(accountToJson.Balance, 'E', -1, 64)
		s.addRecord(APIstub, argsnew)
		if i == 0 {
			break
		}
	}

	return shim.Success(nil)
}

/*
 *  wallet方法，即查看用户信息方法，需要指定用户ID
 */
func (s *SmartContract) wallet(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	if len(args) != 1 {
		return shim.Error("Query Wallet Failed: Incorrect number of arguments. Expecting 1")
	}

	accountAsBytes, queryErr := APIstub.GetState(args[0])
	if queryErr != nil {
		fmt.Println("Query Wallet Failed:" + queryErr.Error())
		return shim.Error("Query Wallet Failed:" + queryErr.Error())
	}
	return shim.Success(accountAsBytes)
}

/*
 *  record方法，即查看交易记录方法，需要指定用户ID
 */
func (s *SmartContract) record(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	if len(args) != 1 {
		return shim.Error("Query Record Failed: Incorrect number of arguments. Expecting 1")
	}
	//	 querySql := "{\"selector\": {\"from\": \"" + args[0] + "\"}}"
	queryString := fmt.Sprintf(`{"selector": {"$or": [{"from": %v},{"to": %v}]}}`, args[0], args[0])

	recordAsBytes, queryErr := APIstub.GetQueryResult(queryString)
	if queryErr != nil {
		fmt.Println("Query Record Failed:" + queryErr.Error())
		return shim.Error(queryErr.Error())
	}
	defer recordAsBytes.Close() //释放迭代器

	var buffer bytes.Buffer
	bArrayMemberAlreadyWritten := false
	buffer.WriteString(`{"records":[`)

	for recordAsBytes.HasNext() {
		queryResponse, err := recordAsBytes.Next()
		if err != nil {
			return shim.Error("Query Record Failed:" + err.Error())
		}
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		buffer.WriteString(string(queryResponse.Value))
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString(`]}`)
	fmt.Print("Record Query Result: %s", buffer.String())
	return shim.Success(buffer.Bytes())
}

/*
 *  指定日期区间查询用户的交易记录，输入参数为用户ID（args[0]），开始日期（args[1]），结束日期（args[2]）
 */
func (s *SmartContract) recordByDate(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	if len(args) != 3 {
		return shim.Error("Query Income And Expense By Date Failed: Incorrect number of arguments. Expecting 3")
	}

	queryString := fmt.Sprintf(`{"selector": {"$or": [{"from": %v},{"to": %v}],"date": {"$gt": %v,"$lt": %v}}}`, args[0], args[0], args[1], args[2])
	recordAsBytes, queryErr := APIstub.GetQueryResult(queryString)
	if queryErr != nil {
		fmt.Println("Query Income And Expense By Date Failed:" + queryErr.Error())
		return shim.Error(queryErr.Error())
	}
	defer recordAsBytes.Close()

	countIncome := make(map[string]string)
	countExpense := make(map[string]string)
	countTotal := make(map[string]string)
	lastTime := make(map[string]int64)
	countDays := list.New()

	for recordAsBytes.HasNext() {
		queryRes, err := recordAsBytes.Next()
		if err != nil {
			return shim.Error("Query Income And Expense By Date Failed:" + err.Error())
		}
		record := Record{}
		json.Unmarshal(queryRes.Value, &record)
		// 收入
		if _, ok := countIncome[record.Date]; ok {
			curMoney, _ := strconv.ParseFloat(countIncome[record.Date], 64)
			countIncome[record.Date] = strconv.FormatFloat(curMoney+record.Money, 'E', -1, 64)
		} else {
			countIncome[record.Date] = strconv.FormatFloat(record.Money, 'E', -1, 64)
			countDays.PushBack(record.Date)
		}
		// 支出
		if _, ok := countExpense[record.Date]; ok {
			curMoney, _ := strconv.ParseFloat(countExpense[record.Date], 64)
			countExpense[record.Date] = strconv.FormatFloat(curMoney+record.Money, 'E', -1, 64)
		} else {
			countExpense[record.Date] = strconv.FormatFloat(record.Money, 'E', -1, 64)
		}
		// 当日余额
		if _, ok := lastTime[record.Date]; ok {
			if record.Timestamp >= lastTime[record.Date] {
				lastTime[record.Date] = record.Timestamp
				if record.From == args[0] {
					countTotal[record.Date] = strconv.FormatFloat(record.FromBalance, 'E', -1, 64)
				} else {
					countTotal[record.Date] = strconv.FormatFloat(record.ToBalance, 'E', -1, 64)
				}
			}
		} else {
			lastTime[record.Date] = record.Timestamp
			if record.From == args[0] {
				countTotal[record.Date] = strconv.FormatFloat(record.FromBalance, 'E', -1, 64)
			} else {
				countTotal[record.Date] = strconv.FormatFloat(record.ToBalance, 'E', -1, 64)
			}
		}
	}
	var buffer bytes.Buffer
	bArrayMemberAlreadyWritten := false
	buffer.WriteString(`{"records":[`)

	for i := countDays.Front(); i != nil; i = i.Next() {
		incomeExpense := IncomeExpense{}
		recordDate := i.Value.(string)
		incomeExpense.Date = recordDate
		incomeExpense.Income = countIncome[recordDate]
		incomeExpense.Expense = countExpense[recordDate]
		incomeExpense.TotalBalance = countTotal[recordDate]
		if bArrayMemberAlreadyWritten == true {
			buffer.WriteString(",")
		}
		res, _ := json.Marshal(incomeExpense)
		buffer.WriteString(string(res))
		bArrayMemberAlreadyWritten = true
	}
	buffer.WriteString(`]}`)
	fmt.Print("Query Income And Expense By Date Result: %s", buffer.String())
	return shim.Success(buffer.Bytes())
}

/*
 *  更新账户信息表中余额，当前流程为：在account表中修改余额
 */
func (s *SmartContract) updateBalance(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	fmt.Println("Update Balance Start")
	if len(args) != 2 {
		return shim.Error("Update Balance Failed: Incorrect number of arguments. Expecting 2")
	}

	accountAsBytes, queryErr := APIstub.GetState(args[0])
	if queryErr != nil {
		fmt.Println("Update Balance Failed:" + queryErr.Error())
		return shim.Error(queryErr.Error())
	}
	account := Account{}
	json.Unmarshal(accountAsBytes, &account)
	account.Balance, _ = strconv.ParseFloat(args[1], 32)
	fmt.Println("Update Balance:", args[1])
	accountAsBytes, _ = json.Marshal(account)

	err := APIstub.PutState(args[0], accountAsBytes)
	if err != nil {
		fmt.Println("Update Balance Failed:" + err.Error())
		return shim.Error(err.Error())
	}

	fmt.Println("Update Balance End")
	return shim.Success(nil)
}

/*
 *  新增交易记录，当前流程为：在交易记录表增加一条记录
 */
func (s *SmartContract) addRecord(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	if len(args) != 3 {
		return shim.Error("Add Record Failed: Incorrect number of arguments. Expecting 3")
	}
	t := time.Now()
	id := strconv.FormatInt(t.Unix(), 10)
	var timeLayoutStr = "2006-01-02 15:04:05" //go中的时间格式化必须是这个时间
	curTime := t.Format(timeLayoutStr)
	money, _ := strconv.ParseFloat(args[2], 64)
	frombalance, _ := strconv.ParseFloat(args[4], 64)
	tobalance, _ := strconv.ParseFloat(args[5], 64)
	uuid, _ := getUUID()
	record := Record{uuid, curTime[0:10], curTime[11:19], args[0], args[1], money, args[3], t.Unix(), frombalance, tobalance}
	recordAsBytes, _ := json.Marshal(record)
	err := APIstub.PutState(id, recordAsBytes)
	if err != nil {
		fmt.Println("Add Record Failed:" + err.Error())
		return shim.Error(err.Error())
	}
	return shim.Success(nil)
}

/*
 * 根据userId查询资金流向
 */
func (s *SmartContract) fundFlow(APIstub shim.ChaincodeStubInterface, args []string) sc.Response {
	if len(args) != 1 {
		return shim.Error("Query Fund Flow Failed: Incorrect number of arguments. Expecting 1")
	}
	queryString := fmt.Sprintf(`{"selector": "parentId": {"$regex" : "*"}}`)
	purseAsBytes, purseErr := APIstub.GetQueryResult(queryString)
	if purseErr != nil {
		fmt.Println("Query Fund Flow Failed:" + purseErr.Error())
		return shim.Error(purseErr.Error())
	}
	defer purseAsBytes.Close()

	var buffer bytes.Buffer
	bArrayMemberAlreadyWritten := false
	buffer.WriteString(`{"flows":[`)

	for purseAsBytes.HasNext() {
		purseNext, err := purseAsBytes.Next()
		if err != nil {
			return shim.Error("Query Fund Flow Failed:" + err.Error())
		}
		purse := Purse{}
		json.Unmarshal(purseNext.Value, &purse)
		if isUserPurse(APIstub, purse.Id, args[0]) {
			flow := Flow{}
			flow.TargetId = purse.UserId
			flow.Amount = purse.Balance

			if bArrayMemberAlreadyWritten == true {
				buffer.WriteString(",")
			}
			res, _ := json.Marshal(flow)
			buffer.WriteString(string(res))
			bArrayMemberAlreadyWritten = true

			buffer.WriteString(`]}`)
		}
	}
	return shim.Success(buffer.Bytes())
}

/*
 *  判断当前钱包源头是否是该user
 */
func isUserPurse(APIstub shim.ChaincodeStubInterface, id string, userid string) bool {
	purseAsBytes, _ := APIstub.GetState(id)
	purse := Purse{}
	parentPurse := Purse{}
	json.Unmarshal(purseAsBytes, &purse)
	parentPurseBytes, parentErr := APIstub.GetState(purse.ParentId)
	if parentErr != nil {
		return false
	} else {
		json.Unmarshal(parentPurseBytes, &parentPurse)
		if userid == parentPurse.UserId {
			return true
		} else {
			return isUserPurse(APIstub, parentPurse.Id, userid)
		}
	}
}

/*
 * 生成uuid
 */
func getUUID() (string, error) {
	u2, err := uuid.NewV4()
	if err != nil {
		fmt.Printf("Something went wrong: %s", err)
		return "", err
	}
	return u2.String(), nil
}

func main() {
	err := shim.Start(new(SmartContract))
	if err != nil {
		fmt.Printf("Error starting SmartContract: %s", err)
	}
}
