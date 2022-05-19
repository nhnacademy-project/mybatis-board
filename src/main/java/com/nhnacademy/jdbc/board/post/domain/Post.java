package com.nhnacademy.jdbc.board.post.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Post {

    public static final Character DELETED = 'Y';
    public static final Character NOT_DELETED = 'N';

    private Long postNo;
    private final Long userNo;
    private final String title;
    private final String content;
    private final Date createdAt;
    private Character deleteNY;
    private String filePath;
    private Date modifiedAt;
    private Long modifiedNo;

    public Post(Long userNo, String title, String content, Date createdAt) {
        this.userNo = userNo;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

}
