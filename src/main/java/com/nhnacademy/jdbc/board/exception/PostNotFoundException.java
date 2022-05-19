package com.nhnacademy.jdbc.board.exception;

public class PostNotFoundException extends IllegalArgumentException {
    public PostNotFoundException() {
        super("해당 게시물이 존재하지 않습니다.");
    }
}
