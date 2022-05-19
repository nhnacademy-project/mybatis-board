package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;

import java.util.Optional;

public interface PostService {
    Optional<Post> getPost(long postNo);
    void insertPost(PostInsertRequest postInsertRequest);

}
