package com.example.springbootfabricdemo.dto.fabric.req;

import lombok.Data;

import java.util.Date;

@Data
public class TxQuery {

    String sourceId;

    String targetId;

    String startTime;

    String endTime;

    /**
     * 当前用户
     */
    String userId;

}
