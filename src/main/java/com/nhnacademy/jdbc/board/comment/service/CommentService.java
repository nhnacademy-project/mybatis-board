package com.nhnacademy.jdbc.board.comment.service;

import com.nhnacademy.jdbc.board.comment.dto.request.CommentRequest;
import com.nhnacademy.jdbc.board.comment.dto.response.CommentResponse;
import java.util.List;

public interface CommentService {

    void insertComment(CommentRequest comment);

    List<CommentResponse> findComments(Long pageNo);
}
