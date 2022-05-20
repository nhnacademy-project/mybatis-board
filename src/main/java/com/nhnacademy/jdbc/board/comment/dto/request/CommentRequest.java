package com.nhnacademy.jdbc.board.comment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentRequest {

    private final String content;
    private final Long postNo;
    private final Long writerNo;
}
