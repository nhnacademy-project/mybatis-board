package com.nhnacademy.jdbc.board.post.mapper;

import com.nhnacademy.jdbc.board.post.domain.Post;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * #FIXME: @Service 사용 여부?
 * DefaultPostService 클래스 PostMapper 가 AutoWired 되지 않아
 * 생성자 파라미터 오류가 발생.
 */

@Service
public interface PostMapper {

    void insertPost(Post post);
    List<Post> selectStudents();
}
