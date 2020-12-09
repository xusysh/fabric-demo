package com.example.springbootfabricdemo.web;

import com.example.springbootfabricdemo.dto.fabric.req.TxQuery;
import com.example.springbootfabricdemo.dto.fabric.req.TxSubmit;
import com.example.springbootfabricdemo.dto.resp.Response;
import com.example.springbootfabricdemo.entity.User;
import com.example.springbootfabricdemo.entity.fabric.AccountInfo;
import com.example.springbootfabricdemo.entity.fabric.TxInfo;
import com.example.springbootfabricdemo.service.TxService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tx")
@AllArgsConstructor
public class TxController {

    TxService txService;

    /**
     * 转账
     * @param txSubmit
     * @return
     */
    @PostMapping("/submit")
    public Response<AccountInfo> transfer(@RequestBody TxSubmit txSubmit) throws Exception {
        AccountInfo accountInfo = txService.transfer(txSubmit);
        return Response.newSuccInstance(accountInfo);
    }

    /**
     * 筛选指定时间范围和交易双方用户的交易信息
     * @param txQuery
     * @return
     */
    @PostMapping("/filter")
    public Response<List<TxInfo>> filterTxInfo(@RequestBody TxQuery txQuery) throws Exception {
        List<TxInfo> txInfoList = txService.filter(txQuery);
        return Response.newSuccInstance(txInfoList);
    }

    /**
     * 筛选指定用户的所有交易信息
     * @param userId
     * @return
     */
    @GetMapping("/filter/{userId}")
    public Response<List<TxInfo>> filterTxInfo(@PathVariable("userId") String userId) throws Exception {
        List<TxInfo> txInfoList = txService.filterUser(userId);
        return Response.newSuccInstance(txInfoList);
    }

}
