package com.nhnacademy.jdbc.board.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {

    @NotNull
    private final String username;

    @NotNull
    private final String password;
}
