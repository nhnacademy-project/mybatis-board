package com.nhnacademy.jdbc.board.post.mapper;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.domain.ReadPost;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Param;

public interface PostMapper {

    void insertPost(Post post);

    Optional<ReadPost> findPostById(@Param("postNo") Long postNo);

    List<ReadPost> findNotDeletedPosts();

    List<ReadPost> findAllPosts();
}
