package com.nhnacademy.jdbc.board.comment.web;

import com.nhnacademy.jdbc.board.comment.dto.request.CommentRequest;
import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.exception.ContentLengthOverException;
import com.nhnacademy.jdbc.board.exception.NoAuthorizationException;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postNo}")
    public ModelAndView writeComment(@PathVariable("postNo") Long postNo,
                                     @RequestParam("content") String content,
                                     HttpSession session) {

        if (content.length() > 250) {
            throw new ContentLengthOverException();
        }

        UserLoginResponse user =
            Optional.ofNullable((UserLoginResponse) session.getAttribute("user"))
                    .orElseThrow(NoAuthorizationException::new);

        commentService.insertComment(CommentRequest.builder()
                                                   .content(content)
                                                   .postNo(postNo)
                                                   .writerNo(user.getUserNo())
                                                   .build());

        return new ModelAndView("redirect:/post/" + postNo);
    }
}
