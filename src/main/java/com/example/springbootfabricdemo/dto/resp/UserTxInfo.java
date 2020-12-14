package com.example.springbootfabricdemo.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户交易信息（数组元素）
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTxInfo {

    /**
     * income/outcome
     */
    String type;

    Long timestamp;

    String time;

    String cpId;

    Double amount;

    Double balance;

    String comment;

}
