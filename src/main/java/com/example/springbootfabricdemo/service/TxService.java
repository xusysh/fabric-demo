package com.example.springbootfabricdemo.service;

import com.example.springbootfabricdemo.entity.fabric.TxInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@RequestMapping("/tx")
public class TxService {

    @PostMapping("/transfer")
    public List<TxInfo> transfer() {
        return null;
    }

}
