package com.example.springbootfabricdemo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.springbootfabricdemo.config.FabricConfig;
import com.example.springbootfabricdemo.dto.fabric.req.TxQuery;
import com.example.springbootfabricdemo.dto.fabric.req.TxSubmit;
import com.example.springbootfabricdemo.entity.fabric.AccountInfo;
import com.example.springbootfabricdemo.entity.fabric.TxInfo;
import com.example.springbootfabricdemo.fabric.FabricComponent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TxService {

    @Autowired
    FabricComponent fabricComponent;

    @Autowired
    FabricConfig fabricConfig;

    public AccountInfo transfer(TxSubmit txSubmit) throws Exception {
        // todo: app用户映射到fabric用户
        String resultStr = fabricComponent.invokeTx(
                fabricConfig.getUserId(), "donate", txSubmit.getUserId(), txSubmit.getTargetId(), txSubmit.getAmount(), txSubmit.getComment());
//        AccountInfo accountInfo = JSON.parseObject(resultStr, AccountInfo.class);
        return null;
    }

    public List<TxInfo> filter(TxQuery txQuery) throws Exception {
        // todo: app用户映射到fabric用户
        String resultStr = fabricComponent.invokeQuery(
                fabricConfig.getUserId(), "recordByCondition", txQuery.getSourceId(), txQuery.getTargetId(), txQuery.getStartTime(), txQuery.getEndTime());
        Map<String, JSONArray> resultMap = JSON.parseObject(resultStr, Map.class);
        JSONArray records = resultMap.get("records");
        List<TxInfo> txInfoList = records.toJavaList(TxInfo.class);
        return txInfoList;
    }

    public List<TxInfo> filterUser(String userId) throws Exception {
        // todo: app用户映射到fabric用户
        String resultStr = fabricComponent.invokeQuery(
                fabricConfig.getUserId(), "record", userId);
        Map<String, JSONArray> resultMap = JSON.parseObject(resultStr, Map.class);
        JSONArray records = resultMap.get("records");
        List<TxInfo> txInfoList = records.toJavaList(TxInfo.class);
        Map<String, List<TxInfo>> userTxInfo = txInfoList.stream().collect(Collectors.groupingBy(TxInfo::getRemark));
        //todo:merge
        List<TxInfo> userTxInfoList = txInfoList;
//        userTxInfo.values().stream().map(txInfos -> {
//            if(txInfos.size() == 1) return txInfos.get(0);
//            else {
//                txInfos.stream().
//            }
//        });
        //数组里的数两两组合比较，按照比较值更得的顺序升序排序
        userTxInfoList.sort(Comparator.comparing(TxInfo::getTimestamp).reversed());
        return userTxInfoList;
    }

}