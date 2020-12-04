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

}
