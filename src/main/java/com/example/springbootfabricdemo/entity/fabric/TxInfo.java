package com.example.springbootfabricdemo.entity.fabric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TxInfo {

    String Id;

    /**
     * 交易发生时间
     */
    Long Timestamp;

    /**
     * 交易日期
     */
    String Date;

    /**
     * 交易时间
     */
    String Time;

    /**
     * 付款方用户id
     */
    String From;

    /**
     * 收款方用户id
     */
    String To;

    /**
     * 转账金额
     */
    Double Money;

    /**
     * 付款方余额
     */
    Double FromBalance;

    /**
     * 收款方余额
     */
    Double ToBalance;

    /**
     * 金额id
     */
    String PurseId;

    /**
     * 交易标记
     */
    String remark;

    /**
     * 备注
     */
    String comment;

}
