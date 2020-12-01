package com.example.springbootfabricdemo.dto.fabric.req;

import lombok.Data;

import java.util.Date;

@Data
public class TxQuery {

    String sourceId;

    String targetId;

    Date startTime;

    Date endTime;

}
