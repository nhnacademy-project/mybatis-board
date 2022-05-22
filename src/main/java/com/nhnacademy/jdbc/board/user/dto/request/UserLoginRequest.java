package com.nhnacademy.jdbc.board.user.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {

    @NotBlank(message = "아이디를 입력해야 합니다.")
    private final String username;

    @NotBlank(message = "비밀번호를 입력해야 합니다.")
    private final String password;
}
