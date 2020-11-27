package com.example.springbootfabricdemo.service;

import com.example.springbootfabricdemo.entity.AccountInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/account")
public class AccountService {

    public AccountInfo getAccountInfo(String userId) {
        return null;
    }

}
