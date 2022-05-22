package com.nhnacademy.jdbc.board.comment.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SelectComment {

    private final String content;
    private final String writerName;
    private final Date createdAt;
}
