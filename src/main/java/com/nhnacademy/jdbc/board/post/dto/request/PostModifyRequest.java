package com.nhnacademy.jdbc.board.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class PostModifyRequest {

    @Setter
    private final String title;

    @Setter
    private final String content;

    @Setter
    private final Date modifiedAt;

    @Setter
    private final Long modifyUserNo;
}
