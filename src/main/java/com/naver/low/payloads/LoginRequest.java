package com.naver.low.payloads;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    private String userEmail;

    @NotBlank
    private String password;
}
