package com.nhnacademy.jdbc.board.post.web.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.post.domain.Page;
import com.nhnacademy.jdbc.board.post.dto.response.PostResponse;
import com.nhnacademy.jdbc.board.post.service.DefaultPostService;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;
import com.nhnacademy.jdbc.board.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;

public class PagingTest {

    MockMvc mockMvc;
    PostService postService;
    CommentService commentService;

    UserService userService;

    @BeforeEach
    void setUp() {
        postService = mock(DefaultPostService.class);
        commentService = mock(CommentService.class);
        userService = mock(UserService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postService, commentService, userService)).build();
    }


    @Test
    @DisplayName("페이지네이션")
    void paging() throws Exception {

        MockHttpSession session = new MockHttpSession();
        PostResponse postResponse = mock(PostResponse.class);

        Page<PostResponse> page = mock(Page.class);

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(postService.getTotalPage()).thenReturn(1);
        when(postService.findPagedPosts(anyInt(), anyInt(),anyBoolean())).thenReturn(page);

        mockMvc.perform(get("/post/posts").session(session))
                .andExpect(status().isOk())
               .andExpect(view().name("post/posts"));
    }

    @Test
    @DisplayName("최소 페이지 미만 요청")
    void paging_lower_bound_error() throws Exception {
        mockMvc.perform(get("/post/posts?page=0"))
               .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("최대 페이지 초과 요청")
    void paging_upper_bound_error() throws Exception {
        int page = 10;
        when(postService.getTotalPage()).thenReturn(page - 1);
        mockMvc.perform(get("/post/posts?page=" + page))
               .andExpect(status().is3xxRedirection());

    }
}
