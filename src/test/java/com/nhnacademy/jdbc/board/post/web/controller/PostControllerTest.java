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
import com.nhnacademy.jdbc.board.exception.ModifyAccessException;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;
import com.nhnacademy.jdbc.board.post.dto.request.PostModifyRequest;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;
import com.nhnacademy.jdbc.board.user.service.UserService;
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
    @DisplayName("게시물 작성폼 진입")
    void insertIntoForm() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        mockMvc.perform(get("/post/write")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("post/post-form"));
    }

    @Test
    @DisplayName("게시물 작성 에러")
    void insertWriteValidationFailedException() throws Exception {

        PostService postService = mock(PostService.class);
        PostInsertRequest postInsertRequest = mock(PostInsertRequest.class);
        BindingResult bindingResult = mock(BindingResult.class);
        PostController postController = new PostController(postService,null ,null);
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

        mockMvc.perform(get("/post/modify/"+1)
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("post/post-form"));
    }

    @Test
    @DisplayName("게시물 수정{ 관리자}")
    @Rollback
    void modify() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        MultiValueMap<String, String> insertValues = new LinkedMultiValueMap<>();

        insertValues.add("title", "하이");
        insertValues.add("content", "여긴 250자를 적어줘야해서 너무 많음.");

        when(mockLoginUser.getUserNo()).thenReturn(1L);

        mockMvc.perform(post("/post/modify/"+1)
                .session(session)
                .params(insertValues))
                .andExpect(status().is3xxRedirection());


    }


    @Test
    @DisplayName("게시물 삭제 {관리자}")
    @Rollback
    void deleteAdmin() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(mockLoginUser.isAdmin()).thenReturn(true);

        mockMvc.perform(get("/post/delete/" +1)
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("게시물 삭제 {유저}")
    @Rollback
    void deleteUser() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(!mockLoginUser.isAdmin()).thenReturn(true);

        mockMvc.perform(get("/post/delete/" +1)
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("게시물 삭제 예외처리{삭제 권한없는 유저}")
    @Rollback
    void deleteUserThrowByModifyAccessException() throws Exception {

        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService,null ,null);

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(mockLoginUser.getUserNo()).thenReturn(2L);

        assertThatThrownBy(() -> postController.doDelete(4L,session))
                .isInstanceOf(ModifyAccessException.class);
    }

    @Test
    @DisplayName("게시물 복구 {관리자}")
    @Rollback
    void retoreAdmin() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(mockLoginUser.isAdmin()).thenReturn(true);

        mockMvc.perform(get("/post/restore/" +1)
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("게시물 복구 예외처리 {관리자가 아닌 유저}")
    @Rollback
    void retoreIsNotAdminModifyAccessException() throws Exception {

        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService,null ,null);

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(mockLoginUser.getUserNo()).thenReturn(2L);

        assertThatThrownBy(() -> postController.doRestore(4L,session))
                .isInstanceOf(ModifyAccessException.class);
    }

}
