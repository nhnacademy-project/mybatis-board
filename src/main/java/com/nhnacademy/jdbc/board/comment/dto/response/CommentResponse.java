package com.nhnacademy.jdbc.board.comment.dto.response;

import com.nhnacademy.jdbc.board.comment.domain.SelectComment;
import java.util.Date;
import lombok.Getter;

@Getter
public class CommentResponse {

    private final String content;
    private final String writerName;
    private final Date createdAt;

    public CommentResponse(SelectComment comment) {
        this.content = comment.getContent();
        this.writerName = comment.getWriterName();
        this.createdAt = comment.getCreatedAt();
    }
}
