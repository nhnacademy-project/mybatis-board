package com.nhnacademy.jdbc.board.post.service;

import static java.util.stream.Collectors.toList;

import com.nhnacademy.jdbc.board.exception.PostNotFoundException;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;
import com.nhnacademy.jdbc.board.post.dto.response.PostResponse;
import com.nhnacademy.jdbc.board.post.mapper.PostMapper;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostMapper postMapper;

    @Override
    public void insertPost(PostInsertRequest postInsertRequest) {

        Post post = new Post(postInsertRequest.getUserNo()
            , postInsertRequest.getTitle()
            , postInsertRequest.getContent()
            , new Date()
        );

        postMapper.insertPost(post);
    }

    @Override
    public PostResponse findPostById(Long postNo) {
        return postMapper.findPostById(postNo)
                         .map(PostResponse::new)
                         .orElseThrow(PostNotFoundException::new);
    }

    @Override
    public List<PostResponse> findNotDeletedPosts() {
        return postMapper.findNotDeletedPosts()
                         .stream()
                         .map(PostResponse::new)
                         .collect(toList());
    }

    @Override
    public List<PostResponse> findAllPosts() {
        return postMapper.findAllPosts()
                         .stream()
                         .map(PostResponse::new)
                         .collect(toList());
    }

}
