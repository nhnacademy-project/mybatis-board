package com.nhnacademy.jdbc.board.user.service;

import com.nhnacademy.jdbc.board.user.dto.request.UserLoginRequest;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;

public interface UserService {

    UserLoginResponse doLogin(UserLoginRequest userLoginRequest);

    String findModifierNameByUserNo(Long modifyUserNo);
}
