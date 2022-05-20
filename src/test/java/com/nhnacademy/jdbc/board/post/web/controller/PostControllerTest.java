package com.nhnacademy.jdbc.board.post.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;
import com.nhnacademy.jdbc.board.post.dto.request.PostModifyRequest;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.domain.User;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        PostController postController = new PostController(postService);
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



//        MultiValueMap<String, String> insertValues = new LinkedMultiValueMap<>();

//        insertValues.add("title", "하이이ㅣ이이이이이이이이이이이ㅣ이이이이이ㅣ이이잉");
//        insertValues.add("content", "여긴 250자를 적어줘야해서 너무 많음.");
//
//        MvcResult mvcResult = (MvcResult) mockMvc.perform(post("/post/login")
//                        .param("username", "admin")
//                        .param("password", "1234"))
//                .andExpect((ResultMatcher) post("/post/write")
//                        .param("title", String.valueOf(insertValues.get("title")))
//                        .param("content", String.valueOf(insertValues.get("content"))))
//                .andExpect(cont);
//
//        assertThat(mvcResult.getModelAndView()).isExactlyInstanceOf(ValidationFailedException.class);
//
//        MvcResult mvcResult1 = mockMvc.perform(post("/login")
//                        .param("username", "admin")
//                        .param("password", "1234"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/"))
//                .andReturn();
    }


//    @Test
//    @DisplayName("게시물 작성 에러2")
//    void insertWriteError() throws Exception {
//
//        PostService postService = mock(PostService.class);
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        PostController postController = new PostController(postService);
//
//        when(postController.hasErrors()).thenReturn(true);
//
//        assertThatThrownBy(() -> postController.doInsert())
//
//    }

//    @Test
//    @DisplayName("게시물 수정폼 진입")
//    void modify() throws Exception {
//        mockMvc.perform(get("/post/modify/{postNo}"));
//    }
