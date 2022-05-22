package com.nhnacademy.jdbc.board.post.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class PostInsert {

    private final Long userNo;
    private final String title;
    private final String content;
}
