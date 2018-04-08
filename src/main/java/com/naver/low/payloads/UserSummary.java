package com.naver.low.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSummary {

    private Long id;
    private String username;
    private String useremail;

}
