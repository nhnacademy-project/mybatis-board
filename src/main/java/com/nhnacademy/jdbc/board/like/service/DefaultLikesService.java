package com.nhnacademy.jdbc.board.like.service;

import static java.util.stream.Collectors.*;

import com.nhnacademy.jdbc.board.like.domain.Likes;
import com.nhnacademy.jdbc.board.like.mapper.LikesMapper;
import com.nhnacademy.jdbc.board.post.dto.response.PostResponse;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultLikesService implements LikesService {

    private final LikesMapper likesMapper;

    @Override
    public boolean findLikesByPostNoAndUserNo(Long postNo, Long userNo) {
        return Objects.nonNull(likesMapper.selectLikes(postNo, userNo));
    }

    @Override
    public List<PostResponse> findLikeList(Long userNo) {
        return likesMapper.findLikeList(userNo)
            .stream()
            .map(PostResponse::new)
            .collect(toList());
    }

    @Transactional
    @Override
    public void insertLikes(Likes likes) {
        likesMapper.insertLikes(likes);
    }

    @Transactional
    @Override
    public void deleteLikes(Likes likes) {
        likesMapper.deleteLikes(likes);
    }
}
