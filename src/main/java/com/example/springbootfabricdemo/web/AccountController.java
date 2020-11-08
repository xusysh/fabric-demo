package com.example.springbootfabricdemo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/info/{userId}")
    public Map<String,Object> getAccountInfo(@PathVariable String userId) {
        return null;
    }

}
