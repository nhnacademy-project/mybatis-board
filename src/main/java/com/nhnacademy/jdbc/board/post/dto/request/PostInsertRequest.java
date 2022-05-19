package com.nhnacademy.jdbc.board.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostInsertRequest {
    //private final Long userNo; 필요 없는 이유 세션으로 받아 올것이기 때문 임.
    private final String title;
    private final String content;
}
