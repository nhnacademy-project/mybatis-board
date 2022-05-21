package com.nhnacademy.jdbc.board.post.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PostModifyRequest {

    private final Long postNo;

    @Size(max = 25)
    @NotBlank
    private final String title;

    @Size(max = 250)
    @NotBlank
    private final String content;

    @Setter
    private Long modifyUserNo;

    public PostModifyRequest(Long postNo, String title, String content) {
        this.postNo = postNo;
        this.title = title;
        this.content = content;
    }
}
