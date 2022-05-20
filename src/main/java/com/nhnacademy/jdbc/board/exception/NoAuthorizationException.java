package com.nhnacademy.jdbc.board.exception;

public class NoAuthorizationException extends IllegalArgumentException {
    public NoAuthorizationException() {
        super("권한이 없습니다.");
    }
}
