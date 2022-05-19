package com.nhnacademy.jdbc.board.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

    private static final Character IS_ADMIN = 'Y';
    private static final Character IS_NOT_ADMIN = 'N';

    private final Long userNo;
    private final String username;
    private final String name;
    private final String password;
    private final Character adminNY;
}
