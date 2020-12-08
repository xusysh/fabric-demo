package com.example.springbootfabricdemo.web;

import com.example.springbootfabricdemo.dto.resp.Response;
import com.example.springbootfabricdemo.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

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
