package com.nhnacademy.jdbc.board.like.web;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import com.nhnacademy.jdbc.board.like.domain.Likes;
import com.nhnacademy.jdbc.board.like.service.LikesService;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(classes = {RootConfig.class}),
        @ContextConfiguration(classes = {WebConfig.class})
})
class LikesControllerTest {

    private MockMvc mockMvc;
    private MockHttpSession session;
    private UserLoginResponse user;
    private LikesService likesService;

    @BeforeEach
    void setUp() {

//        user = new UserLoginResponse(1L, null, null, null);
        user = mock(UserLoginResponse.class);

        likesService = mock(LikesService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new LikesController(likesService))
                .build();
        session = new MockHttpSession();
        session.setAttribute("user", user);
    }

    @AfterEach
    void tearDown() {
        session.clearAttributes();
    }

    @Test
    @DisplayName("좋아요한 게시물 목록 조회")
    void likeList() throws Exception {

        when(user.getUserNo()).thenReturn(1L);

        mockMvc.perform(get("/like/list")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("post/likes"));
    }

    @Test
    @DisplayName("좋아요 클릭")
    void registerLike() throws Exception {

        when(user.getUserNo()).thenReturn(1L);

        mockMvc.perform(get("/like/register/{postNo}", 1)
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("좋아요 목록이 없는 유저가 리스트를 조회할 경우")
    void isNotExistLikes() throws Exception {

        when(user.getUserNo()).thenReturn(2L);

        mockMvc.perform(get("/like/cancel/" + 2)
                        .session(session))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    @DisplayName("좋아요 삭제하기")
    @Rollback
    void deleteLike() throws Exception {
        when(user.getUserNo()).thenReturn(1L);

        mockMvc.perform(get("/like/cancel/" + 1L)
                .session(session))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @DisplayName("존재하는 좋아요 또 클릭")
    void registerLikeAgain() throws Exception {

        when(likesService.findLikesByPostNoAndUserNo(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(get("/like/register/{postNo}", 1)
                        .session(session))
                .andExpect(status().is3xxRedirection());
    }
}
