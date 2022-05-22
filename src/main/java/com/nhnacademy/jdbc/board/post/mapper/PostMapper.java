package com.nhnacademy.jdbc.board.post.mapper;

import com.nhnacademy.jdbc.board.post.domain.DeletePost;
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

    List<ReadPost> findPagedPosts(@Param("offset") Integer offset);

    List<ReadPost> findFilterPagedPosts(@Param("offset") Integer offset);

    void modifyPostByNo(Post post);

    void deletePostByNo(@Param("postNo") Long postNo);

    Integer selectPageCount();

    void restorePostsByNo(Long postNo);

    List<ReadPost> findSearchFilterPagedPosts(@Param("offset") Integer offset, @Param("search") String search);

    List<ReadPost> findSearchPagedPosts(@Param("offset") Integer offset, @Param("search") String search);
}
