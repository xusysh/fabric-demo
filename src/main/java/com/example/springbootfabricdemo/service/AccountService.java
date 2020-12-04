package com.example.springbootfabricdemo.service;

import com.alibaba.fastjson.JSON;
import com.example.springbootfabricdemo.dto.fabric.req.TxQuery;
import com.example.springbootfabricdemo.entity.User;
import com.example.springbootfabricdemo.entity.fabric.AccountInfo;
import com.example.springbootfabricdemo.entity.fabric.TxInfo;
import com.example.springbootfabricdemo.fabric.FabricComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequestMapping("/account")
public class AccountService {

    @Autowired
    FabricComponent fabricComponent;

    @GetMapping("/account")
    public User getAccountInfo(String userId) throws Exception {
        String resultStr = fabricComponent.invokeQuery(
                "user1", "wallet", userId);
        AccountInfo accountInfo = JSON.parseObject(resultStr, AccountInfo.class);
        return null;
    }


    @PostMapping("/transfer")
    public List<TxInfo> queryTxInfo(@RequestBody TxQuery txQuery) throws Exception {
        // todo: app用户映射到fabric用户
        fabricComponent.invokeQuery(
                "user1", "wallet", txQuery.getSourceId());
        return null;
    }

}
