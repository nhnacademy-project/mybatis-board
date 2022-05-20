package com.nhnacademy.jdbc.board.comment.mapper;

import com.nhnacademy.jdbc.board.comment.domain.InsertComment;
import com.nhnacademy.jdbc.board.comment.domain.SelectComment;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {

    void insertComment(InsertComment comment);

    List<SelectComment> findComments(@Param("postNo") Long postNo);
}
