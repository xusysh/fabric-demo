package com.example.springbootfabricdemo.service;

import com.example.springbootfabricdemo.entity.User;
import com.example.springbootfabricdemo.dto.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/auth")
public class AuthService {

    @PostMapping("/enroll-admin")
    public Response<User> enrollAdmin() {
        return null;
    }

    /**
     * 注册用户
     * @return
     */
    @PostMapping("/register-user")
    public Response<User> registerUser() {
        return null;
    }

}
