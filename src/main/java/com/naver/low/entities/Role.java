package com.naver.low.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String name;

}
