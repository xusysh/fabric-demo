package com.example.springbootfabricdemo.service;

import com.example.springbootfabricdemo.entity.AccountInfo;
import com.example.springbootfabricdemo.entity.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/auth")
public class AuthService {

    @PostMapping("/enroll-admin")
    public Response<AccountInfo> enrollAdmin() {
        return null;
    }

    /**
     * 注册用户
     * @return
     */
    @PostMapping("/register-user")
    public Response<AccountInfo> registerUser() {
        return null;
    }

}
