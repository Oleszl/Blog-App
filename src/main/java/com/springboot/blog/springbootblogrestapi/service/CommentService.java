package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.dto.comment.CommentDto;
import com.springboot.blog.springbootblogrestapi.dto.comment.CommentDtoRequest;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDtoRequest commentDtoRequest);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateCommentById(Long postId, Long commentId, CommentDtoRequest commentDtoRequest);

    void deleteCommentById(Long postId, Long commentId);
}
