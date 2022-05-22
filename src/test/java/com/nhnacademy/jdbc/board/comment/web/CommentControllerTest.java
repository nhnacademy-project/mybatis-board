package com.nhnacademy.jdbc.board.comment.web;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import com.nhnacademy.jdbc.board.user.dto.response.UserLoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy({
    @ContextConfiguration(classes = {RootConfig.class}),
    @ContextConfiguration(classes = {WebConfig.class})
})
class CommentControllerTest {

    private MockMvc mockMvc;
    private MockHttpSession session;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                                 .build();
        session = new MockHttpSession();
    }

    @AfterEach
    void tearDown() {
        session.clearAttributes();
    }

    @Test
    @DisplayName("댓글 추가")
    @Rollback
    void insertComment() throws Exception {

        UserLoginResponse user = mock(UserLoginResponse.class);
        session.setAttribute("user", user);
        when(user.getUserNo()).thenReturn(1L);

        mockMvc.perform(post("/comment/{postNo}", 1)
            .param("content", "content")
            .session(session))
            .andExpect(status().is3xxRedirection());
    }
}