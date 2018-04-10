package com.naver.low.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile {

    private Long id;
    private String username;
    private String useremail;
    // coins, points and webtoons when this user is a webtoonist
    // webtoons this user liked when this user is a regular user
}
