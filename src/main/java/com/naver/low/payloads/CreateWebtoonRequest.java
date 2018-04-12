package com.naver.low.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class CreateWebtoonRequest {

    private String webtoonTitle;

    private String webtoonDescription;

    @JsonIgnore
    private MultipartFile webtoonImage;

    @JsonIgnore
    private MultipartFile webtoonThumbnail;

    @JsonProperty("webtoonImage")
    private String webtoonImageFileName;

    @JsonProperty("webtoonThumbnail")
    private String webtoonThumbnailFileName;


}
