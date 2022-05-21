package com.nhnacademy.jdbc.board.like.mapper;

import com.nhnacademy.jdbc.board.like.domain.Likes;
import com.nhnacademy.jdbc.board.post.domain.ReadPost;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LikesMapper {

    Likes selectLikes(@Param("postNo") Long postNo, @Param("userNo") Long userNo);

    List<ReadPost> findLikeList(@Param("userNo") Long userNo);

    void insertLikes(Likes likes);

    void deleteLikes(Likes likes);
}
