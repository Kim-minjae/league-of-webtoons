package com.naver.low.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Role {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(name = "role_name")
    private RoleName name;

    public Role(RoleName name) {
        this.name = name;
    }

    public Role() {

    }


}
