package com.nhnacademy.jdbc.board.user.web;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.nhnacademy.jdbc.board.config.RootConfig;
import com.nhnacademy.jdbc.board.config.WebConfig;
import com.nhnacademy.jdbc.board.exception.ValidationFailedException;
import com.nhnacademy.jdbc.board.user.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextHierarchy({
    @ContextConfiguration(classes = {RootConfig.class}),
    @ContextConfiguration(classes = {WebConfig.class})
})
class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @DisplayName("로그인 폼 진입")
    void loginForm() throws Exception {
        mockMvc.perform(get("/login"))
               .andExpect(status().isOk())
               .andExpect(view().name("login/login-form"));
    }

    @Test
    @DisplayName("로그인 실패")
    void login_fail() {
        UserService userService = mock(UserService.class);
        BindingResult bindingResult = mock(BindingResult.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        UserController userController = new UserController(userService);

        when(bindingResult.hasErrors()).thenReturn(true);

        assertThatThrownBy(() -> userController.doLogin(null, bindingResult, request))
            .isInstanceOf(ValidationFailedException.class);
    }

    @Test
    @DisplayName("로그인 성공")
    void doLogin() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/login")
                                         .param("username", "user")
                                         .param("password", "1234"))
                                     .andExpect(status().is3xxRedirection())
                                     .andExpect(view().name("redirect:/"))
                                     .andReturn();

        HttpSession session = mvcResult.getRequest().getSession(false);
        assertThat(session).isNotNull();
    }

}