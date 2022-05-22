package com.nhnacademy.jdbc.board.post.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class PostInsertRequest {

    @Setter
    private Long userNo;

    @NotNull
    @Size(max = 25)
    private final String title;

    @NotNull
    @Size(max = 250)
    private final String content;

    @Null
    private final MultipartFile file;
}
