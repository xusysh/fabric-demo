package com.example.springbootfabricdemo.service;

import com.example.springbootfabricdemo.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/account")
public class AccountService {

    public User getAccountInfo(String userId) {
        return null;
    }

}
