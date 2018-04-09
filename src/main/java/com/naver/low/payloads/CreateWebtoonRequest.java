package com.naver.low.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWebtoonRequest {

    private String webtoonTitle;
    private String webtoonDescription;

}
