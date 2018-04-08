package com.naver.low.controllers;

import com.naver.low.payloads.UserSummary;
import com.naver.low.repositories.UserRepository;
import com.naver.low.security.CurrentUser;
import com.naver.low.security.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    //private static final Logger logger = LoggerFactory.getLogger(UserController.class); slf4j 어노테이션 사용하면 자동으로 된다고함

    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/me")
    // @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_WEBTOONIST')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getEmail());
        return userSummary;
    }
}
