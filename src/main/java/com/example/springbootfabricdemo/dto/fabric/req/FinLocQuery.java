package com.example.springbootfabricdemo.dto.fabric.req;

import lombok.Data;

import java.util.Date;

@Data
public class FinLocQuery {

    String userId;

    Date startTime;

    Date endTime;

}
