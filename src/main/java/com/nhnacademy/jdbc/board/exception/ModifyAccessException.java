package com.nhnacademy.jdbc.board.exception;

public class ModifyAccessException extends IllegalArgumentException{

    public ModifyAccessException() {

        super("해당 게시물에 대한 수정 권한이 없습니다.");
    }
}
