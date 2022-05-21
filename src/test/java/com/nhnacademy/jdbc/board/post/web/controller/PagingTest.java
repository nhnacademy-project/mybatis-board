package com.nhnacademy.jdbc.board.post.web.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.comment.service.CommentService;
import com.nhnacademy.jdbc.board.post.domain.Page;
import com.nhnacademy.jdbc.board.post.service.DefaultPostService;
import com.nhnacademy.jdbc.board.post.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class PagingTest {

    MockMvc mockMvc;
    PostService postService;
    CommentService commentService;

    @BeforeEach
    void setUp() {
        postService = mock(DefaultPostService.class);
        commentService = mock(CommentService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postService, commentService, null)).build();
    }


//    @Test
//    @DisplayName("페이지네이션")
//    void paging() throws Exception {
//
//        Page page = mock(Page.class);
//        when(postService.getTotalPage()).thenReturn(1);
//        when(postService.findPagedPosts(anyInt(), eq(1),false)).thenReturn(page);
//
//        mockMvc.perform(get("/post/posts"))
//               .andExpect(status().isOk())
//               .andExpect(view().name("post/posts"));
//    }

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
