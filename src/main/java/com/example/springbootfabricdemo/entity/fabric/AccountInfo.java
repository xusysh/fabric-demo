package com.example.springbootfabricdemo.entity.fabric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

    String Id;

    String UserId;

    String Name;

//    String Password;

    String Balance;

    String OrgId;

}
