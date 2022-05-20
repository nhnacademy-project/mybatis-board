package com.nhnacademy.jdbc.board.exception;

public class ContentLengthOverException extends IllegalArgumentException {
    public ContentLengthOverException() {
        super("입력 가능 길이가 초과하였습니다.");
    }
}
