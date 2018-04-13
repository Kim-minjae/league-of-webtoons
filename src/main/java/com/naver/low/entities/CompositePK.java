package com.naver.low.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CompositePK implements Serializable {
    private Long userOneId;
    private Long userTwoId;
}
