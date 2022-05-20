package com.nhnacademy.jdbc.board.post.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeletePost {

    private final Long postNo;
    private final Character deleteNY;

    @Builder
    public DeletePost(Long postNo, Character deleteNY) {
        this.postNo = postNo;
        this.deleteNY = deleteNY;
    }
}
