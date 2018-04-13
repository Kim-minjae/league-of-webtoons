package com.naver.low.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Table(name = "webtoons")
@Entity
public class Webtoon implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "webtoon_id")
    private Long id;

    @Column(name = "webtoon_title")
    private String webtoonTitle;

    @Column(name = "webtoon_description")
    private String webtoonDescription;

    @Column(name = "webtoon_thumbnail")
    private String webtoonThumbnail;

    @Column(name = "webtoon_image")
    private String webtoonImage;

    @Column(name = "webtoon_hits")
    private int webtoonHits;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userLikesWebtoons")
    private Set<User> webtoonLikedByAccounts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User webtoonist;

    public Webtoon(String webtoonTitle, String webtoonDescription, String webtoonThumbnail, String webtoonImage, User webtoonist) {
        this.webtoonTitle = webtoonTitle;
        this.webtoonDescription = webtoonDescription;
        this.webtoonThumbnail = webtoonThumbnail;
        this.webtoonImage = webtoonImage;
        this.webtoonist = webtoonist;
    }
}
