package com.naver.low.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
public class Webtoon implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "webtoon_id")
    private Long id;

    @Column(name = "webtoon_image")
    private String webtoonImage;

    @Column(name = "webtoon_hits")
    private int webtoonHits;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userLikesWebtoons")
    private Set<User> webtoonLikedByAccounts;

    @ManyToOne
    @JoinColumn(name = "webtoonist_id")
    private Webtoonist webtoonist;
}
