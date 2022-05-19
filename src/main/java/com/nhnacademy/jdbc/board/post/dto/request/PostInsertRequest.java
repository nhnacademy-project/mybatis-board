package com.nhnacademy.jdbc.board.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PostInsertRequest {

    @Setter
    private Long userNo;

    private final String title;
    private final String content;
}
