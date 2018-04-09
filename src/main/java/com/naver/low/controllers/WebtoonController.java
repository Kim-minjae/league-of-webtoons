package com.naver.low.controllers;

import com.naver.low.payloads.CreateWebtoonRequest;
import com.naver.low.repositories.WebtoonRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/api/webtoons")
public class WebtoonController {

    WebtoonRepository webtoonRepository;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_WEBTOONIST')")
    public ResponseEntity<?> uploadWebtoon (@RequestParam("file") MultipartFile file, @RequestBody CreateWebtoonRequest createWebtoonRequest) {
        return null;
    }

}
