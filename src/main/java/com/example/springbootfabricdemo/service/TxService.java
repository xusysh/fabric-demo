package com.example.springbootfabricdemo.service;

import com.alibaba.fastjson.JSON;
import com.example.springbootfabricdemo.dto.fabric.req.TxQuery;
import com.example.springbootfabricdemo.dto.fabric.req.TxSubmit;
import com.example.springbootfabricdemo.entity.fabric.AccountInfo;
import com.example.springbootfabricdemo.entity.fabric.TxInfo;
import com.example.springbootfabricdemo.fabric.FabricComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequestMapping("/tx")
public class TxService {

    @Autowired
    FabricComponent fabricComponent;

    @PostMapping("/transfer")
    public List<TxInfo> transfer(@RequestBody TxSubmit txSubmit) throws Exception {
        // todo: app用户映射到fabric用户
        String resultStr = fabricComponent.invokeQuery(
                "user1", "donate", txSubmit.getUserId(), txSubmit.getTargetId(), txSubmit.getAmount());
        AccountInfo accountInfo = JSON.parseObject(resultStr, AccountInfo.class);
        return null;
    }


}
