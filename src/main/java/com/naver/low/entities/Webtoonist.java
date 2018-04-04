package com.naver.low.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "webtoonistPassword")
@Entity
public class Webtoonist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "webtoonist_id")
    private Long id;

    @Column(name = "webtoonist_email")
    private String webtoonistEmail;

    @Column(name = "webtoonist_name")
    private String webtoonistName;

    @JsonIgnore
    @Column(name = "webtoonist_password")
    private String webtoonistPassword;

    @Column(name = "webtoonist_coins")
    private int webtoonistCoins;

    @Column(name = "webtoonist_points")
    private int webtoonistPoints;

    @OneToMany(mappedBy = "webtoonist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Webtoon> webtoons = new ArrayList<>();
}
