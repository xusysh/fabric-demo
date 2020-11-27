package com.example.springbootfabricdemo.web;

import com.example.springbootfabricdemo.entity.AccountInfo;
import com.example.springbootfabricdemo.entity.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("/info/{userId}")
    public Response<AccountInfo> getAccountInfo(@PathVariable String userId) {

        return Response.newSuccInstance(new AccountInfo());
    }

}
