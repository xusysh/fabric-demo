package com.example.springbootfabricdemo.dto.fabric.req;

import lombok.Data;

import java.util.Date;

/**
 * 查询指定时间内，用户根据指定时间段聚合的收入，支出和总计金额
 */
@Data
public class FinQuery {

    String userId;

    String startTime;

    String endTime;

}
