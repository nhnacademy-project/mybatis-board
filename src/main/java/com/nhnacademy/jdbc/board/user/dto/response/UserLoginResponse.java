package com.nhnacademy.jdbc.board.user.dto.response;

import com.nhnacademy.jdbc.board.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {

    private final Long userNo;
    private final String name;
    private final String username;
    private final Character adminNY;

    public boolean isAdmin() {
        return adminNY == User.IS_ADMIN;
    }
}
