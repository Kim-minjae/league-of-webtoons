package com.naver.low.controllers;

import com.naver.low.repositories.WebtoonRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class WebtoonController {

    WebtoonRepository webtoonRepository;


}
