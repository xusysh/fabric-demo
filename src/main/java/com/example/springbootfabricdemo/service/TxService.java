package com.example.springbootfabricdemo.service;

import com.example.springbootfabricdemo.entity.AccountInfo;
import com.example.springbootfabricdemo.entity.Response;
import com.example.springbootfabricdemo.entity.TxResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequestMapping("/tx")
public class TxService {

    @PostMapping("/transfer")
    public Response<TxResult> transfer() {
        return null;
    }

}
