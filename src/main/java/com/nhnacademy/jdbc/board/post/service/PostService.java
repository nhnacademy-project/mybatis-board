package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.domain.Page;
import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;
import com.nhnacademy.jdbc.board.post.dto.request.PostModifyRequest;
import com.nhnacademy.jdbc.board.post.dto.response.PostResponse;

public interface PostService {

    void insertPost(PostInsertRequest postInsertRequest);

    void modifyPost(PostModifyRequest postModifyRequest);

    void deletePost(Long postNo);

    PostResponse findPostByNo(Long postNo);

    Page<PostResponse> findPagedPosts(int page, int totalPage, boolean isFilter);

    int getTotalPage();

    boolean isWriter (Long postNo, Long userNo);

    void restorePost(Long postNo);
}
