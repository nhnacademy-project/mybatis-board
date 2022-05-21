package com.nhnacademy.jdbc.board.like.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Likes {

    private final Long postNo;
    private final Long userNo;
}
