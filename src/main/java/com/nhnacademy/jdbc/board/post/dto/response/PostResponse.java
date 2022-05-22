package com.nhnacademy.jdbc.board.post.dto.response;

import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.domain.ReadPost;
import java.util.Date;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {

    private final Long postNo;
    private final Long writerNo;
    private final String username;
    private final String writer;
    private final String title;
    private final String content;
    private final Date createdAt;
    private Character deleteNY;
    private String filePath;
    private Long modifyUserNo;
    private Date modifiedAt;
    private Integer commentCount;

    public PostResponse(ReadPost post) {
        this.postNo = post.getPostNo();
        this.writerNo = post.getWriterNo();
        this.username = post.getUsername();
        this.writer = post.getWriter();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.deleteNY = post.getDeleteNY();
        this.filePath = post.getFilePath();
        this.modifyUserNo = post.getModifyUserNo();
        this.modifiedAt = post.getModifiedAt();
        this.commentCount = Optional.ofNullable(post.getCommentCount())
                                    .orElse(0);
    }

    public boolean isDeleted() {
        return deleteNY == Post.DELETED;
    }
}
