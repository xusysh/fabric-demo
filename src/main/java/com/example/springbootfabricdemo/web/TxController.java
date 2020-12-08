package com.example.springbootfabricdemo.web;

import com.example.springbootfabricdemo.dto.fabric.req.TxQuery;
import com.example.springbootfabricdemo.dto.resp.Response;
import com.example.springbootfabricdemo.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tx")
public class TxController {

    /**
     * 筛选指定时间范围和交易双方用户的交易信息
     * @param txQuery
     * @return
     */
    @PostMapping("/filter")
    public Response<User> filterTxInfo(@RequestBody TxQuery txQuery) {

        return Response.newSuccInstance(new User());
    }

}
