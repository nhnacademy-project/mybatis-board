package com.nhnacademy.jdbc.board.post.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostModifyRequest {

    @NotNull
    private final Long postNo;

    @Size(max = 25)
    @NotNull
    private final String title;

    @Size(max = 250)
    @NotNull
    private final String content;

    @Setter
    private Long modifyUserNo;

    public PostModifyRequest(Long postNo, String title, String content) {
        this.postNo = postNo;
        this.title = title;
        this.content = content;
    }
}
