package com.example.springbootfabricdemo.web;

import com.example.springbootfabricdemo.entity.User;
import com.example.springbootfabricdemo.dto.resp.Response;
import com.example.springbootfabricdemo.entity.fabric.AccountInfo;
import com.example.springbootfabricdemo.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@AllArgsConstructor
public class AccountController {

    AccountService accountService;

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/info/{userId}")
    public Response<AccountInfo> getAccountInfo(@PathVariable String userId) throws Exception {
        AccountInfo accountInfo = accountService.getAccountInfo(userId);
        return Response.newSuccInstance(accountInfo);
    }

}
