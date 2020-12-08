package com.example.springbootfabricdemo.web;

import com.example.springbootfabricdemo.dto.fabric.req.WalletQuery;
import com.example.springbootfabricdemo.dto.resp.Response;
import com.example.springbootfabricdemo.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stat")
public class StatController {

    /**
     * 获取指定时间范围内用户的交易统计信息
     * @param walletQuery
     * @return
     */
    @GetMapping("/filter")
    public Response<User> getAccountInfo(@RequestBody WalletQuery walletQuery) {
        walletQuery.getUserId();
        return Response.newSuccInstance(new User());
    }

}
