package com.naver.low.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "userPassword")
@Table(name = "users")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_name")
    private String userName;

    @JsonIgnore
    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "webtoonist_coins")
    private int webtoonistCoins;

    @Column(name = "webtoonist_points")
    private int webtoonistPoints;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "webtoonist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Webtoon> webtoons = new HashSet<>();

    // the reason of using Set instead of List
    // https://vladmihalcea.com/the-best-way-to-use-the-manytomany-annotation-with-jpa-and-hibernate/
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_likes_webtoons",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "webtoon_id")})
    private Set<Webtoon> userLikesWebtoons = new HashSet<>();

    @OneToMany(mappedBy = "challenger")
    private Set<Battle> challengers = new HashSet<>();

    @OneToMany(mappedBy = "opponent")
    private Set<Battle> opponents = new HashSet<>();

    public User(String userEmail, String userName, String userPassword) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPassword = userPassword;
    }
}
