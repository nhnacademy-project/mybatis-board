package com.nhnacademy.jdbc.board.comment.domain;

import com.nhnacademy.jdbc.board.comment.dto.request.CommentRequest;
import lombok.Getter;

@Getter
public class InsertComment {

    private final String content;
    private final Long userNo;
    private final Long postNo;

    public InsertComment(CommentRequest request) {
        this.content = request.getContent();
        this.userNo = request.getWriterNo();
        this.postNo = request.getWriterNo();
    }
}
