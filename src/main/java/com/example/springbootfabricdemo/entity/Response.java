package com.example.springbootfabricdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Response<T> {

    private int status;

    private String msg;

    T data;

    public static <T> Response<T> newSuccInstance(T data) {
        return new Response(200, "成功", data);
    }

}
