package com.example.springbootfabricdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Response<T> {

    private int status;

    private String msg;

    T data;

    public Response(int status, String msg, T data) {
        return ;
    }

    public static <T> Response<T> newSuccInstance(T data) {
        return new Response(200, "成功", data);
    }

}
