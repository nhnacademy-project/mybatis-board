package com.nhnacademy.jdbc.board.user.service;

import com.nhnacademy.jdbc.board.exception.UserNotFoundException;
import com.nhnacademy.jdbc.board.user.domain.User;
import com.nhnacademy.jdbc.board.user.dto.request.UserLoginRequest;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;
import com.nhnacademy.jdbc.board.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserLoginResponse doLogin(UserLoginRequest userLoginRequest) {

        User user =
            userMapper.loginUser(userLoginRequest.getUsername(), userLoginRequest.getPassword())
                      .orElseThrow(UserNotFoundException::new);

        return new UserLoginResponse(user.getUserNo(),user.getName(), user.getUsername(), user.getAdminNY());
    }

    @Override
    public String findModifierNameByUserNo(Long modifyUserNo) {
        return userMapper.findModifierNameByUserNo(modifyUserNo);
    }
}
