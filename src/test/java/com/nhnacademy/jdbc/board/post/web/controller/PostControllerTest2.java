//package com.nhnacademy.jdbctest.board.board.board;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import com.nhnacademy.jdbc.board.post.domain.Post;
//import com.nhnacademy.jdbc.board.post.domain.PostView;
//import com.nhnacademy.jdbc.board.post.service.PostService;
//import com.nhnacademy.jdbc.board.post.web.PostController;
//import com.nhnacademy.jdbc.board.user.domain.User;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//public class PostControllerTest2 {
//    private MockMvc mockMvc;
//    private PostService postService;
//    private User user;
//    private Post post;
//    private MockHttpSession session;
//
//    @BeforeEach
//    void setUp() {
//        postService = mock(PostService.class);
//        mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postService))
//                .build();
//        session = new MockHttpSession();
//        user = new User(1,"admin","1234",false);
//        session.setAttribute("login",user);
//        post = new Post(1,"titless","contetn","gilung",null,new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),false);
//    }
//
//    @Test
//    void getPostViewTest() throws Exception {
//        user = (User) session.getAttribute("login");
//        mockMvc.perform(get("/postView/0" ).session(session))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("postViews",postService.viewPosts(0)))
//                .andExpect(model().attribute("page",0))
//                .andExpect(model().attribute("maxPage",postService.getMaxPage()))
//                .andExpect(model().attribute("isAdmin",user.isAdmin()))
//                .andDo(print())
//                .andExpect(view().name("postView"));
//    }
//    @Test
//    void getPostInsertTest() throws Exception {
//        mockMvc.perform(get("/postInsert"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("postInsertForm"));
//    }
//
//    @Test
//    void postPostInsertTest() throws Exception {
//        doNothing().when(postService).createPost(anyLong(),anyString(),anyString());
//
//        MvcResult mvcResult = mockMvc.perform(post("/postInsert")
//                        .session(session)
//                        .param("title", "title1")
//                        .param("content", "content1")
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/postView/0"))
//                .andDo(print())
//                .andReturn();
//
//        verify(postService).createPost(anyLong(),anyString(),anyString());
//        //메소드가 호출 되었는지 확인.
//
//    }
//
//    @Test
//    void getPostDetailTest() throws Exception {
//        //  when(session.getAttribute("login")).thenReturn(user);
//        when(postService.getPost(1)).thenReturn(post);
//        System.out.println("post : "+ post.toString());
////        assertThat(postService.getPost(1)).isEqualTo(post);
//        MvcResult mvcResult = mockMvc.perform(get("/postDetail/{postNum}",1).session(session))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("post", post))
//                .andExpect(model().attribute("comments",postService.viewComments(0)))
//                .andExpect(model().attribute("isWriter", Objects.equals(user.getUserId(), post.getWriterId())))
//                .andDo(print())
//                .andExpect(view().name("postDetailView"))
//                .andReturn();
//
//    }
//
//    @Test
//    void getPostModifyTest() throws Exception{
//        when(postService.getPost(1)).thenReturn(post);
//        MvcResult mvcResult = mockMvc.perform(get("/postModify/1"))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("post", postService.getPost(1)))
//                .andDo(print())
//                .andExpect(view().name("postModifyForm"))
//                .andReturn();
//        assertThat(mvcResult.getModelAndView().getModelMap().getAttribute("post"))
//                .isEqualTo(postService.getPost(1));
//
//    }
//
//    @Test
//    void postPostModifyTest() throws Exception{
//        MvcResult mvcResult = mockMvc.perform(post("/postModify/0").session(session)
//                        .param("title", "title1")
//                        .param("content", "content1"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/postView/0"))
//                .andReturn();
//    }
//
//    @Test
//    void getPostDeleteListTest() throws Exception{
//        List<PostView> postViewList = new ArrayList<>();
//        when(postService.viewPosts(1)).thenReturn(postViewList);
//        when(postService.getMaxPage()).thenReturn(10);
//        MvcResult mvcResult = mockMvc.perform(get("/postDeleteList/1"))
//                .andExpect(status().isOk())
//                .andExpect(model().attribute("postViews", postService.viewPosts(1)))
//                .andExpect(model().attribute("page", 1))
//                .andExpect(model().attribute("maxPage", postService.getMaxPage()))
//                .andDo(print())
//                .andExpect(view().name("postDeleteList"))
//                .andReturn();
//    }
//
//
//    @Test
//    void getPostRestoreTest() throws Exception{
//        MvcResult mvcResult = mockMvc.perform(get("/postRestore/{postNum}",1)
//                        .session(session)
//                )
//                .andExpect(status().is3xxRedirection())
//                .andExpect(view().name("redirect:/postDeleteList/0"))
//                .andDo(print())
//                .andReturn();
//        verify(postService).restorePost(1);
//    }
//
//
//
//}