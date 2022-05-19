package com.nhnacademy.jdbc.board.exception;

public class UserNotFoundException extends IllegalArgumentException {

    public UserNotFoundException() {
        super("아이디 또는 비밀번호가 일치하지 않습니다.");
    }
}
