package com.nhnacademy.jdbc.board.post.web.controller;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.exception.ModifyAccessException;
import com.nhnacademy.jdbc.board.exception.NoAuthorizationException;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import com.nhnacademy.jdbc.board.like.service.LikesService;
import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;
import com.nhnacademy.jdbc.board.post.dto.request.PostModifyRequest;
import com.nhnacademy.jdbc.board.post.dto.response.PostResponse;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.nhnacademy.jdbc.board.user.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final UserService userService;

    private final LikesService likesService;

    @GetMapping(value = "/write")
    public ModelAndView insert(PostInsertRequest postInsertRequest, HttpSession session) {

        if (Objects.isNull(session.getAttribute("user"))) {
            throw new NoAuthorizationException();
        }

        return new ModelAndView("post/post-form").addObject("post", postInsertRequest);
    }

    @PostMapping(value = "/write")
    public ModelAndView doInsert(@ModelAttribute @Valid PostInsertRequest postInsertRequest,
                                 final BindingResult bindingResult, HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        UserLoginResponse userLoginResponse = (UserLoginResponse) session.getAttribute("user");

        if (Objects.isNull(userLoginResponse)) {
            throw new NoAuthorizationException();
        }

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        postInsertRequest.setUserNo(userLoginResponse.getUserNo());

        ModelAndView mav = new ModelAndView("redirect:posts");
        mav.addObject("url", "write");
        postService.insertPost(postInsertRequest);

        return mav;
    }

    @GetMapping("/{postNo}")
    public ModelAndView post(@PathVariable("postNo") Long postNo, HttpServletRequest request, HttpServletResponse response) {

        addViewedPostNoToCookie(request,response,String.valueOf(postNo));
        ModelAndView mav = new ModelAndView("post/post");
      
        PostResponse post = postService.findPostByNo(postNo);

        Long modifyUserNo = post.getModifyUserNo();
        if (Objects.nonNull(modifyUserNo)) {
            String modifierName = userService.findModifierNameByUserNo(modifyUserNo);
            mav.addObject("modifierName", modifierName);
        }

        boolean isLike = false;

        HttpSession session = request.getSession();

        UserLoginResponse user = (UserLoginResponse) session.getAttribute("user");

        if (Objects.nonNull(user)) {
            isLike = likesService.findLikesByPostNoAndUserNo(postNo, user.getUserNo());
        }

        mav.addObject("isLike", isLike);
        mav.addObject("comments", commentService.findComments(postNo));
        mav.addObject("post",post);

        return mav;
    }

    private void addViewedPostNoToCookie(final HttpServletRequest request, final HttpServletResponse response, final String postNo) {
        Cookie accumulateNttIdCookie = Arrays
                .stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("view-count-cookie"))
                .findFirst()
                .orElseGet(() -> {
                    Cookie cookie = createAccNttIdCookie(postNo);    // 조회수 중복 방지용 쿠키 생성
                    response.addCookie(cookie);                        // 생성한 쿠키를 response에 담는다.
                    postService.increaseViewCount(Long.parseLong(postNo));            // 조회수 증가 쿼리 수행
                    return cookie;
                });

        // 한번이라도 조회한 게시물에 대해서는 쿠키값에 해당 게시물의 nttId가 저장된다.
        // 서로 다른 nttId에 대해서는 "/" 로 구분한다.
        // ex) 000000000891/000000000890/000000000889
        String cookieValue = accumulateNttIdCookie.getValue();

        if(!cookieValue.contains(postNo)) {
            String newCookieValue = cookieValue + "/" + postNo;
            accumulateNttIdCookie.setMaxAge(getRemainSecondForTomorrow());
            postService.increaseViewCount(Long.parseLong(postNo));            // 조회수 증가 쿼리 수행
        }
    }

    private Cookie createAccNttIdCookie(String cookieValue) {
        Cookie cookie = new Cookie("view-count-cookie", String.valueOf(cookieValue));
        cookie.setComment("조회수 중복 증가 방지 쿠키");    // 쿠키 용도 설명 기재
        cookie.setMaxAge(getRemainSecondForTomorrow());             // 하루를 준다.
        cookie.setHttpOnly(true);                // 클라이언트 단에서 javascript로 조작 불가
        return cookie;
    }

    // 다음 날 정각까지 남은 시간(초)
    private int getRemainSecondForTomorrow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tomorrow = LocalDateTime.now()
                .plusDays(1L)
                .truncatedTo(ChronoUnit.DAYS);
        return (int) now.until(tomorrow, ChronoUnit.SECONDS);
    }


    @GetMapping("/posts")
    public ModelAndView posts(@RequestParam(name = "page", required = false) Integer page,
                              HttpSession httpSession) {

        UserLoginResponse user = (UserLoginResponse) httpSession.getAttribute("user");

        page = Optional.ofNullable(page).orElse(1);

        if (page < 1) {
            return new ModelAndView("redirect:posts?page=1");
        }

        int totalPage = postService.getTotalPage();
        if (page > totalPage) {
            return new ModelAndView("redirect:posts?page=" + totalPage);
        }

        ModelAndView mav = new ModelAndView("post/posts");
        boolean isFilter = false;

        if (isLoginAdmin(user)) {
            mav.addObject("page", postService.findPagedPosts(page, totalPage, isFilter));
        } else {
            isFilter = true;
            mav.addObject("page", postService.findPagedPosts(page, totalPage, isFilter));
        }
        return mav;
    }

    private boolean isLoginAdmin(UserLoginResponse user) {
        return Objects.nonNull(user) && user.isAdmin();
    }

    @GetMapping(value = "/modify/{postNo}")
    public ModelAndView modify(@PathVariable(name = "postNo") Long postNo, HttpSession session) {

        UserLoginResponse user =
            Optional.ofNullable((UserLoginResponse) session.getAttribute("user"))
                    .orElseThrow(NoAuthorizationException::new);

        if (canNotModify(postNo, user)) {
            throw new ModifyAccessException();
        }

        PostResponse post = postService.findPostByNo(postNo);

        ModelAndView mav = new ModelAndView("post/post-form");

        mav.addObject("url", "/post/modify/" + postNo);
        mav.addObject("post", post);
        mav.addObject("title", post.getTitle());
        mav.addObject("content", post.getContent());
        mav.addObject("createAt", post.getCreatedAt());

        return mav;
    }

    @PostMapping(value = "/modify/{postNo}")
    public ModelAndView doModify(@ModelAttribute PostModifyRequest request, HttpSession session) {

        UserLoginResponse user =
            Optional.ofNullable((UserLoginResponse) session.getAttribute("user"))
                    .orElseThrow(NoAuthorizationException::new);

        if (canNotModify(request.getPostNo(), user)) {
            throw new ModifyAccessException();
        }

        request.setModifyUserNo(user.getUserNo());
        postService.modifyPost(request);

        return new ModelAndView("redirect:/post/posts");
    }

    @GetMapping("/delete/{postNo}")
    public ModelAndView doDelete(@PathVariable("postNo") Long postNo, HttpSession session) {

        UserLoginResponse user =
            Optional.ofNullable((UserLoginResponse) session.getAttribute("user"))
                    .orElseThrow(NoAuthorizationException::new);

        if (canNotModify(postNo, user)) {
            throw new ModifyAccessException();
        }

        postService.deletePost(postNo);

        return new ModelAndView("redirect:/post/posts");
    }

    @GetMapping("/restore/{postNo}")
    public ModelAndView doRestore(@PathVariable("postNo") Long postNo, HttpSession session) {

        UserLoginResponse user =
            Optional.ofNullable((UserLoginResponse) session.getAttribute("user"))
                    .orElseThrow(NoAuthorizationException::new);

        if (!user.isAdmin()) {
            throw new ModifyAccessException();
        }

        postService.restorePost(postNo);

        return new ModelAndView("redirect:/post/posts");

    }

    private boolean canNotModify(Long postNo, UserLoginResponse user) {
        return !(user.isAdmin() || postService.isWriter(postNo, user.getUserNo()));
    }
}
