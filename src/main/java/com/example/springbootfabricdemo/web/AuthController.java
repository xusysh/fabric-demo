package com.example.springbootfabricdemo.web;

import com.example.springbootfabricdemo.dto.req.EnrollAdminReq;
import com.example.springbootfabricdemo.dto.req.RegisterUserReq;
import com.example.springbootfabricdemo.dto.resp.Response;
import com.example.springbootfabricdemo.entity.User;
import com.example.springbootfabricdemo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    AuthService authService;

    @PostMapping("/enroll-admin")
    public Response<Void> enrollAdmin(@RequestBody EnrollAdminReq enrollAdminReq) throws Exception {
        authService.enrollAdmin(enrollAdminReq.getUid(),enrollAdminReq.getPasswd());
        return Response.newSuccInstance(null);
    }

    /**
     * 注册用户
     * @return
     */
    @PostMapping("/register-user")
    public Response<Void> registerUser(@RequestBody RegisterUserReq registerUserReq) throws Exception {
        authService.enrollAdmin(registerUserReq.getUid(),registerUserReq.getOrgId());
        return Response.newSuccInstance(null);
    }

}
