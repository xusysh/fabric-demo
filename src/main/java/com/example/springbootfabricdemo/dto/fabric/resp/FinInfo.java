package com.example.springbootfabricdemo.dto.fabric.resp;

import lombok.Data;

/**
 * 每日资金汇总信息（数组元素）
 */
@Data
public class FinInfo {

    String Date;

    Double Income;

    Double Expense;

    Double TotalBalance;

}
