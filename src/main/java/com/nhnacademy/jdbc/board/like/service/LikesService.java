package com.nhnacademy.jdbc.board.like.service;

import com.nhnacademy.jdbc.board.like.domain.Likes;
import com.nhnacademy.jdbc.board.post.dto.response.PostResponse;
import java.util.List;

public interface LikesService {

    boolean findLikesByPostNoAndUserNo(Long postNo, Long userNo);

    List<PostResponse> findLikeList(Long userNo);

    void insertLikes(Likes likes);

    void deleteLikes(Likes likes);
}
