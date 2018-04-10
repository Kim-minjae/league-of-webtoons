package com.naver.low.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WebtoonSummary {

    private Long id;
    private String webtoonTitle;
    private String webtoonThumbnail;
    private String webtoonistName;

}
