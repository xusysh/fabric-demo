package com.example.springbootfabricdemo.service;

import com.example.springbootfabricdemo.entity.User;
import com.example.springbootfabricdemo.dto.resp.Response;
import com.example.springbootfabricdemo.fabric.FabricComponent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class AuthService {

    @Autowired
    FabricComponent fabricComponent;

    public void enrollAdmin(String uid,String passwd) throws Exception {
        fabricComponent.enrollAdmin(uid,passwd);
    }

    /**
     * 注册用户
     * @return
     */
    public void registerUser(String uid,String orgId) throws Exception {
        fabricComponent.registerUser(uid,orgId);
    }

}
