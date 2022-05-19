package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;
import com.nhnacademy.jdbc.board.post.dto.response.PostResponse;
import java.util.List;

public interface PostService {

    void insertPost(PostInsertRequest postInsertRequest);

    PostResponse findPostById(Long postNo);

    List<PostResponse> findNotDeletedPosts();

    List<PostResponse> findAllPosts();

}
