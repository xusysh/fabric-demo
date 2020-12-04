package com.example.springbootfabricdemo.dto.fabric.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinLocQuery {

    String userId;

    Date startTime;

    Date endTime;

}
