package com.naver.low.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Battle {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "userOneId", column = @Column(name = "challenger_id")),
            @AttributeOverride(name = "userTwoId", column = @Column(name = "opponent_id"))})
    private CompositePK id;

    @ManyToOne
    @JoinColumn(name = "challenger_id", insertable = false, updatable = false)
    private User challenger;

    @ManyToOne
    @JoinColumn(name = "opponent_id", insertable = false, updatable = false)
    private User opponent;

    private int status;

    private Long actionUserId;
}
