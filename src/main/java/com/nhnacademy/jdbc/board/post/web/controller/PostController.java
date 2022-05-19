package com.nhnacademy.jdbc.board.post.web.controller;

import com.nhnacademy.jdbc.board.exception.UserNotFoundException;
import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(value = "/write")
    public ModelAndView insert() {

        return new ModelAndView("post/post-form");
    }

    @PostMapping(value = "/write")
    public ModelAndView doInsert(PostInsertRequest postInsertRequest , HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        UserLoginResponse userLoginResponse = (UserLoginResponse) session.getAttribute("user");

        if(Objects.isNull(userLoginResponse)){
            throw new UserNotFoundException();
        }

        postInsertRequest.setUserNo(userLoginResponse.getUserNo());
        // TODO : 경로 변경 (리스트 뷰) 필요.
        ModelAndView mav = new ModelAndView("redirect:/");
        mav.addObject("url","write");
        postService.insertPost(postInsertRequest);

        return mav;
    }

    @GetMapping(value = "/modify/{postNo}")
    public ModelAndView modify(@PathVariable(name = "postNo") Long postNo , HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        UserLoginResponse userLoginResponse = (UserLoginResponse) session.getAttribute("user");

        ModelAndView mav = new ModelAndView("post-form");

    }
}
