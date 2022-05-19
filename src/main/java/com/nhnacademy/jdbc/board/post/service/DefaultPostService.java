package com.nhnacademy.jdbc.board.post.service;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.mapper.PostMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DefaultPostService implements PostService {

    private final PostMapper postMapper;

    public DefaultPostService(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public Optional<Post> getPost(long postNo) {
        return Optional.empty();
    }

    @Override
    public void insertPost(Post post) {
        postMapper.insertPost(post);
    }
}
