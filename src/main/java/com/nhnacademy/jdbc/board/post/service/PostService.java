package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;
import com.nhnacademy.jdbc.board.post.dto.request.PostModifyRequest;
import com.nhnacademy.jdbc.board.post.dto.response.PostResponse;
import com.nhnacademy.jdbc.board.user.domain.User;

import java.util.List;

public interface PostService {

    void insertPost(PostInsertRequest postInsertRequest);

    void modifyPost(PostModifyRequest postModifyRequest);

    PostResponse findPostByNo(Long postNo);

    List<PostResponse> findNotDeletedPosts();

    List<PostResponse> findAllPosts();

    boolean isWriter (Long postNo, Long userNo);

}
