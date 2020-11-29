package com.example.springbootfabricdemo.entity.fabric;

import lombok.Data;

@Data
public class PurseInfo {

    String id;

    /**
     * 余额
     */
    Double balance;

    /**
     * 所属用户id
     */
    String userId;

    /**
     * 父id
     */
    String parentId;

}
