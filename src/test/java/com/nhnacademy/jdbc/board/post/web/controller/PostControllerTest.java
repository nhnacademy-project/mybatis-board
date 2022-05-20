package com.nhnacademy.jdbc.board.post.web.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(classes = {RootConfig.class}),
        @ContextConfiguration(classes = {WebConfig.class})
})
class PostControllerTest {

    private MockMvc mockMvc;
    private MockHttpSession session;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
    }

    @AfterEach
    void tearDown() {
        session.clearAttributes();
    }

    @Test
    @DisplayName("게시글 목록 조회")
    void postList() throws Exception {
        mockMvc.perform(get("/post/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/posts"));
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void postSelectByPostNo() throws Exception {
        mockMvc.perform(get("/post/{postNo}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("post/post"));
    }

    @Test
    @DisplayName("게시물 폼 진입")
    void insertIntoForm() throws Exception {
        mockMvc.perform(get("/post/write"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/post-form"));
    }

    @Test
    @DisplayName("게시물 작성 에러")
    void insertWriteError() throws Exception {

        PostService postService = mock(PostService.class);
        PostInsertRequest postInsertRequest = mock(PostInsertRequest.class);
        BindingResult bindingResult = mock(BindingResult.class);
        PostController postController = new PostController(postService, null);
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        when(bindingResult.hasErrors()).thenReturn(true);

        assertThatThrownBy(() -> postController.doInsert(postInsertRequest, bindingResult, httpServletRequest))
                .isInstanceOf(ValidationFailedException.class);
    }

    @Test
    @DisplayName("게시물 작성 성공")
    @Rollback
    void insertWriteSuccess() throws Exception {

        MultiValueMap<String, String> insertValues = new LinkedMultiValueMap<>();

        insertValues.add("title", "하이");
        insertValues.add("content", "여긴 250자를 적어줘야해서 너무 많음.");
        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);
        when(mockLoginUser.getUserNo()).thenReturn(1L);

        mockMvc.perform(post("/post/write")
                        .session(session)
                        .params(insertValues))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    @DisplayName("게시물 수정 폼 진입")
    void modifyIntoForm() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(mockLoginUser.isAdmin()).thenReturn(true);

        mockMvc.perform(get("/post/modify/" + 1)
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("post/post-form"));
    }
}
