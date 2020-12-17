package com.example.springbootfabricdemo.dto.fabric.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxSubmit {

    String userId;

    String targetId;

    String amount;

    String comment;

    /**
     * 0为不匿名（默认），1为匿名
     */
    String isAnon;

}
