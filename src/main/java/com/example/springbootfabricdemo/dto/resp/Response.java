package com.example.springbootfabricdemo.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

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
