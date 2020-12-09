package com.example.springbootfabricdemo.dto.fabric.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 查询指定时间内，用户所有收入和支出的交易信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletQuery {

    String userId;

    String startTime;

    String endTime;

}
