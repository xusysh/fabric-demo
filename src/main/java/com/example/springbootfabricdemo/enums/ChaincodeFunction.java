package com.example.springbootfabricdemo.enums;

import lombok.AllArgsConstructor;

public enum ChaincodeFunction {

    /**
     * 前后端权限token校验错误
     */
    NO_TOKEN_ERROR("500001", "无登录信息，请重新登录"),
    TOKEN_EXPIRE_ERROR("500002", "登录信息过期，请重新登录"),
    TOKEN_WRONG_ERROR("500003", "登录信息错误，请重新登录"),
    MULTIPLE_LOGIN_ERROR("500003", "该用户已登录"),
    ;

    String chaincodeName;

    String functionName;

    ChaincodeFunction(String chaincodeName, String functionName) {
    }

}
