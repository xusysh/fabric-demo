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
//@Table(name = "ORG")
public class Org {

    @Id
    String id;

    String orgName;

    String remark;

}
