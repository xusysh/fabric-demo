package com.example.springbootfabricdemo.service;

import com.example.springbootfabricdemo.entity.User;
import com.example.springbootfabricdemo.dto.resp.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class AuthService {

    public Response<User> enrollAdmin() {
        return null;
    }

    /**
     * 注册用户
     * @return
     */
    public Response<User> registerUser() {
        return null;
    }

}
