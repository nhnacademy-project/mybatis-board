package com.nhnacademy.jdbc.board.like.web;

import com.nhnacademy.jdbc.board.exception.NoAuthorizationException;
import com.nhnacademy.jdbc.board.like.domain.Likes;
import com.nhnacademy.jdbc.board.like.service.LikesService;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/like")
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/list")
    public ModelAndView likeList(HttpSession session) {

        UserLoginResponse user =
            Optional.ofNullable((UserLoginResponse) session.getAttribute("user"))
                    .orElseThrow(NoAuthorizationException::new);

        ModelAndView mav = new ModelAndView("post/likes");

        mav.addObject("likeList", likesService.findLikeList(user.getUserNo()));
        return mav;
    }

    @GetMapping("/register/{postNo}")
    public ModelAndView registerLikes(@PathVariable(name = "postNo") Long postNo, HttpSession session) {

        UserLoginResponse user =
            Optional.ofNullable((UserLoginResponse) session.getAttribute("user"))
                    .orElseThrow(NoAuthorizationException::new);

        if (!isExist(postNo, user.getUserNo())) {
            likesService.insertLikes(new Likes(postNo, user.getUserNo()));
        }
        return new ModelAndView("redirect:/post/" + postNo);
    }

    @GetMapping("/cancel/{postNo}")
    public ModelAndView cancelLikes(@PathVariable(name = "postNo") Long postNo, HttpSession session) {

        UserLoginResponse user =
            Optional.ofNullable((UserLoginResponse) session.getAttribute("user"))
                    .orElseThrow(NoAuthorizationException::new);

        if (isExist(postNo, user.getUserNo())) {
            likesService.deleteLikes(new Likes(postNo, user.getUserNo()));
        }
        return new ModelAndView("redirect:/post/" + postNo);
    }

    private boolean isExist(Long postNo, Long userNo) {
        return likesService.findLikesByPostNoAndUserNo(postNo, userNo);
    }
}
