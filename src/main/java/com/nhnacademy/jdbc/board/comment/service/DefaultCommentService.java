package com.nhnacademy.jdbc.board.comment.service;

import static java.util.stream.Collectors.*;

import com.nhnacademy.jdbc.board.comment.domain.InsertComment;
import com.nhnacademy.jdbc.board.comment.dto.request.CommentRequest;
import com.nhnacademy.jdbc.board.comment.dto.response.CommentResponse;
import com.nhnacademy.jdbc.board.comment.mapper.CommentMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultCommentService implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public void insertComment(CommentRequest comment) {
        commentMapper.insertComment(new InsertComment(comment));
    }

    @Override
    public List<CommentResponse> findComments(Long postNo) {
        return commentMapper.findComments(postNo)
            .stream()
            .map(CommentResponse::new)
            .collect(toList());
    }
}
