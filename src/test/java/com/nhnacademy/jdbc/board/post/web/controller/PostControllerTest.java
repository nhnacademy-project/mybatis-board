package com.nhnacademy.jdbc.board.post.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
class PostControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
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
    void post() throws Exception {
        mockMvc.perform(get("/post/{postNo}", 1))
               .andExpect(status().isOk())
               .andExpect(view().name("post/post"));
    }

}