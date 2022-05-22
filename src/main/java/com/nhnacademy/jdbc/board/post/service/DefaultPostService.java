package com.nhnacademy.jdbc.board.post.service;

import static java.util.stream.Collectors.toList;

import com.nhnacademy.jdbc.board.exception.PostNotFoundException;
import com.nhnacademy.jdbc.board.post.domain.Page;
import com.nhnacademy.jdbc.board.post.domain.Post;
import com.nhnacademy.jdbc.board.post.domain.PostInsert;
import com.nhnacademy.jdbc.board.post.domain.PostInsertWithFile;
import com.nhnacademy.jdbc.board.post.domain.ReadPost;
import com.nhnacademy.jdbc.board.post.dto.request.PostInsertRequest;
import com.nhnacademy.jdbc.board.post.dto.request.PostModifyRequest;
import com.nhnacademy.jdbc.board.post.dto.response.PostResponse;
import com.nhnacademy.jdbc.board.post.mapper.PostMapper;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

    private final PostMapper postMapper;

    @Transactional
    @Override
    public void insertPost(PostInsertRequest postInsertRequest) {

        if (Objects.isNull(postInsertRequest.getFile())) {
            insert(postInsertRequest);
            return;
        }
        insertWithFile(postInsertRequest);
    }

    private void insertWithFile(PostInsertRequest postInsertRequest) {
        PostInsertWithFile post =
            PostInsertWithFile.builder()
                              .userNo(postInsertRequest.getUserNo())
                              .title(postInsertRequest.getTitle())
                              .content(postInsertRequest.getContent())
                              .filePath(postInsertRequest.getFile()
                                                         .getOriginalFilename())
                              .build();

        postMapper.insertWithFile(post);
    }

    private void insert(PostInsertRequest postInsertRequest) {
        PostInsert post = PostInsert.builder()
                                    .userNo(postInsertRequest.getUserNo())
                                    .title(postInsertRequest.getTitle())
                                    .content(postInsertRequest.getContent())
                                    .build();

        postMapper.insertPost(post);
    }

    @Transactional
    @Override
    public void modifyPost(PostModifyRequest postModifyRequest) {

        Post post = Post.builder()
                        .postNo(postModifyRequest.getPostNo())
                        .title(postModifyRequest.getTitle())
                        .content(postModifyRequest.getContent())
                        .modifyUserNo(postModifyRequest.getModifyUserNo())
                        .build();

        postMapper.modifyPostByNo(post);
    }

    @Transactional
    @Override
    public void deletePost(Long postNo) {
        postMapper.deletePostByNo(postNo);


    }

    @Override
    public void restorePost(Long postNo) {
        postMapper.restorePostsByNo(postNo);
    }

    @Override
    public PostResponse findPostByNo(Long postNo) {
        return postMapper.findPostById(postNo)
                         .map(PostResponse::new)
                         .orElseThrow(PostNotFoundException::new);
    }

    @Override
    public Page<PostResponse> findPagedPosts(int page, int totalPage, boolean isFilter) {

        if (page < 1) {
            page = 1;
        }

        if (page > totalPage) {
            page = totalPage;
        }

        Integer offset = 20 * (page - 1);
        List<ReadPost> list;
        if (isFilter) {
            list = postMapper.findFilterPagedPosts(offset);
        } else {
            list = postMapper.findPagedPosts(offset);
        }

        int start = ((page - 1) / 5) * 5 + 1;
        int end = Math.min(start + 4, totalPage);

        return Page.<PostResponse>builder()
                   .pageList(list.stream()
                                 .map(PostResponse::new)
                                 .collect(toList()))
                   .page(page)
                   .start(start)
                   .end(end)
                   .totalPage(totalPage)
                   .build();
    }

    @Override
    public int getTotalPage() {
        int pageCount = postMapper.selectPageCount();
        return pageCount % 20 == 0 ? pageCount / 20 : pageCount / 20 + 1;
    }

    @Override
    public boolean isWriter(Long postNo, Long userNo) {
        ReadPost readPost = postMapper.findPostById(postNo)
                                      .orElseThrow(PostNotFoundException::new);
        return Objects.equals(readPost.getWriterNo(), userNo);
    }
}
