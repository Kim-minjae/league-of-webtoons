package com.naver.low.controllers;

import com.naver.low.repositories.WebtoonRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/webtoons")
public class WebtoonController {

    WebtoonRepository webtoonRepository;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_WEBTOONIST')")
    public ResponseEntity<?> uploadWebtoon () {
        return null;
    }

}
