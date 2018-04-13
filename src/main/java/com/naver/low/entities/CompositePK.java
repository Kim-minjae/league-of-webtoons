package com.naver.low.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class CompositePK implements Serializable {
    private Long userOneId;
    private Long userTwoId;
}
