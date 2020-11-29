package com.example.springbootfabricdemo.entity;

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
@Entity
@Table(name = "ACCOUNT_INFO")
public class AccountInfo {

    @Id
    @Column(name = "id")
    String id;

    @Column(name = "USER_NAME")
    String userName;

    String password;

    String privateKey;

    String balance;

}
