package com.nhnacademy.jdbc.board.user.mapper;

import com.nhnacademy.jdbc.board.user.domain.User;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    Optional<User> loginUser(@Param("username") String username, @Param("password") String password);
}
