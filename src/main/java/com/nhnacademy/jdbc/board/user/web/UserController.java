package com.nhnacademy.jdbc.board.user.web;

import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import com.nhnacademy.jdbc.board.user.dto.request.UserLoginRequest;
import com.nhnacademy.jdbc.board.user.service.UserService;
import jakarta.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login/login-form");
    }

    @PostMapping("/login")
    public ModelAndView doLogin(@Valid @ModelAttribute UserLoginRequest userLoginRequest
        , BindingResult bindingResult, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        ModelAndView mav = new ModelAndView("redirect:/");
        HttpSession session = request.getSession(true);
        session.setAttribute("user", userService.doLogin(userLoginRequest));

        return mav;
    }
}
