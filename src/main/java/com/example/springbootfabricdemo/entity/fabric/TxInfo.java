package com.example.springbootfabricdemo.entity.fabric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TxInfo {

    String id;

    /**
     * 交易发生时间
     */
    Date timestamp;

    /**
     * 交易日期
     */
    String date;

    /**
     * 交易时间
     */
    String time;

    /**
     * 付款方用户id
     */
    String sourceUid;

    /**
     * 收款方用户id
     */
    String targetUid;

    /**
     * 转账金额
     */
    Double balance;

    /**
     * 付款方余额
     */
    Double sourceBalance;

    /**
     * 收款方余额
     */
    Double targetBalance;

    /**
     * 金额id
     */
    String purseId;

    /**
     * 交易标记
     */
    String remark;

}
