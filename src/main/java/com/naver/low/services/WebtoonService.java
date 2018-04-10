package com.naver.low.services;

import com.naver.low.repositories.WebtoonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebtoonService {

    @Autowired
    private WebtoonRepository webtoonRepository;

}
