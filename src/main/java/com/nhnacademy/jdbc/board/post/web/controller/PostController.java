package com.nhnacademy.jdbc.board.post.web.controller;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;
import com.nhnacademy.jdbc.board.post.service.DefaultPostService;
import com.nhnacademy.jdbc.board.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping
public class PostController {
    private final PostService postService;

    public PostController(DefaultPostService defaultPostService){
        this.postService = defaultPostService;
    }

    @PostMapping(value = "/post/insert")
    public ModelAndView insert(PostInsertRequest postRequest , HttpServletRequest request) {
        /**
         * FIXME = ModelAndView 가 게시글 목록 html 로 변경하기
         *
         */
        HttpSession session = request.getSession(true);
        User user = session.getAttribute("user");

        ModelAndView mav = new ModelAndView("index.html");
        Post post = new Post(postRequest.getUserNo()
                , postRequest.getTitle()
                , postRequest.getContent()
                , new Date()
                );
        postService.insertPost(postRequest);

        return mav;
    }

}
