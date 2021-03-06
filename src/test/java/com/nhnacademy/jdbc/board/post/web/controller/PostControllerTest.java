package com.nhnacademy.jdbc.board.post.web.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.nhnacademy.jdbc.board.post.dto.response.PostResponse;
import com.nhnacademy.jdbc.board.post.service.PostService;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockCookie;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

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
    @DisplayName("????????? ?????? ??????")
    void postList() throws Exception {
        mockMvc.perform(get("/post/posts"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/posts"));
    }

    @Test
    @DisplayName("????????? ?????? ??????")
    void postSelectByPostNo() throws Exception {

        MockCookie mockCookie = new MockCookie("view-count-cookie","1");

        mockMvc.perform(get("/post/{postNo}",2).cookie(mockCookie))
                .andExpect(status().isOk())
                .andExpect(view().name("post/post"));
    }

    @Test
    @DisplayName("????????? ????????? ??????")
    void insertIntoForm() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        mockMvc.perform(get("/post/write")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("post/post-form"));
    }

    @Test
    @DisplayName("????????? ?????? ??????")
    void insertWriteValidationFailedException() throws Exception {

        PostService postService = mock(PostService.class);
        PostInsertRequest postInsertRequest = mock(PostInsertRequest.class);
        BindingResult bindingResult = mock(BindingResult.class);
        PostController postController = new PostController(postService,null ,null,null);

        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();

        when(bindingResult.hasErrors()).thenReturn(true);

        assertThatThrownBy(() -> postController.doInsert(postInsertRequest, bindingResult, httpServletRequest))
                .isInstanceOf(ValidationFailedException.class);
    }

    @Test
    @DisplayName("????????? ?????? ??????")
    @Rollback
    void insertWriteSuccess() throws Exception {

        MultiValueMap<String, String> insertValues = new LinkedMultiValueMap<>();

        insertValues.add("title", "??????");
        insertValues.add("content", "?????? 250?????? ?????????????????? ?????? ??????.");

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);
        when(mockLoginUser.getUserNo()).thenReturn(1L);

        mockMvc.perform(post("/post/write")
                        .session(session)
                        .params(insertValues))
                .andExpect(status().is3xxRedirection());
    }


    @Test
    @DisplayName("????????? ?????? ??? ??????")
    void modifyIntoForm() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(mockLoginUser.isAdmin()).thenReturn(true);

        mockMvc.perform(get("/post/modify/" + 1)
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("post/post-form"));
    }

    @Test
    @DisplayName("????????? ??????{ ?????????}")
    @Rollback
    void modify() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        MultiValueMap<String, String> insertValues = new LinkedMultiValueMap<>();

        insertValues.add("title", "??????");
        insertValues.add("content", "?????? 250?????? ?????????????????? ?????? ??????.");

        when(mockLoginUser.getUserNo()).thenReturn(1L);

        mockMvc.perform(post("/post/modify/" + 1)
                        .session(session)
                        .params(insertValues))
                .andExpect(status().is3xxRedirection());


    }


    @Test
    @DisplayName("????????? ?????? {?????????}")
    @Rollback
    void deleteAdmin() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(mockLoginUser.isAdmin()).thenReturn(true);

        mockMvc.perform(get("/post/delete/" + 1)
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("????????? ?????? {??????}")
    @Rollback
    void deleteUser() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(!mockLoginUser.isAdmin()).thenReturn(true);

        mockMvc.perform(get("/post/delete/" + 1)
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("????????? ?????? ????????????{?????? ???????????? ??????}")
    @Rollback
    void deleteUserThrowByModifyAccessException() throws Exception {

        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService,null ,null,null);

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(mockLoginUser.getUserNo()).thenReturn(2L);

        assertThatThrownBy(() -> postController.doDelete(4L, session))
                .isInstanceOf(ModifyAccessException.class);
    }

    @Test
    @DisplayName("????????? ?????? {?????????}")
    @Rollback
    void restoreAdmin() throws Exception {

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(mockLoginUser.isAdmin()).thenReturn(true);

        mockMvc.perform(get("/post/restore/" + 1)
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("????????? ?????? ???????????? {???????????? ?????? ??????}")
    @Rollback
    void restoreIsNotAdminModifyAccessException() throws Exception {

        PostService postService = mock(PostService.class);
        PostController postController = new PostController(postService,null ,null,null);

        UserLoginResponse mockLoginUser = mock(UserLoginResponse.class);
        session.setAttribute("user", mockLoginUser);

        when(mockLoginUser.getUserNo()).thenReturn(2L);

        assertThatThrownBy(() -> postController.doRestore(4L, session))
                .isInstanceOf(ModifyAccessException.class);
    }

    @Test
    @DisplayName("????????? ?????? ??????")
    void searchToTitlePosts() throws Exception {
        String searchKeyword = "hello";
        mockMvc.perform(get("/post/search?search="+searchKeyword))
                .andExpect(status().isOk())
                .andExpect(view().name("post/posts"));
    }
}
