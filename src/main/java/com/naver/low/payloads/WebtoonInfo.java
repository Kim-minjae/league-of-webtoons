package com.naver.low.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WebtoonInfo {

    private Long id;
    private String webtoonTitle;
    private String webtoonDescription;
    private String webtoonImage;
    private int webtoonHits;
    private int webtoonLikedByAccountsCount;
    private String webtoonist;

}
