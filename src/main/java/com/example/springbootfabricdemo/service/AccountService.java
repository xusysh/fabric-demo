package com.example.springbootfabricdemo.service;

import com.alibaba.fastjson.JSON;
import com.example.springbootfabricdemo.config.FabricConfig;
import com.example.springbootfabricdemo.dto.fabric.req.TxQuery;
import com.example.springbootfabricdemo.entity.User;
import com.example.springbootfabricdemo.entity.fabric.AccountInfo;
import com.example.springbootfabricdemo.entity.fabric.TxInfo;
import com.example.springbootfabricdemo.fabric.FabricComponent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    FabricComponent fabricComponent;

    @Autowired
    FabricConfig fabricConfig;

    public AccountInfo getAccountInfo(String userId) throws Exception {
        String resultStr = fabricComponent.invokeQuery(
                fabricConfig.getUserId(), "wallet", userId);
        AccountInfo accountInfo = JSON.parseObject(resultStr, AccountInfo.class);
        return accountInfo;
    }

    public List<TxInfo> queryTxInfo(@RequestBody TxQuery txQuery) throws Exception {
        // todo: app用户映射到fabric用户
        fabricComponent.invokeQuery(
                fabricConfig.getUserId(), "wallet", txQuery.getSourceId());
        return null;
    }

}
