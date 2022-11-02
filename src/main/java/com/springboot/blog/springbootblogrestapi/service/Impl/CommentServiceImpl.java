package com.springboot.blog.springbootblogrestapi.service.Impl;

import com.springboot.blog.springbootblogrestapi.dto.comment.CommentDto;
import com.springboot.blog.springbootblogrestapi.dto.comment.CommentDtoRequest;
import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.exceptions.BlogAPIException;
import com.springboot.blog.springbootblogrestapi.exception.exceptions.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private ModelMapper mapper;


    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(long postId, CommentDtoRequest commentDtoRequest) {
//        Comment comment = mapToEntity(commentDto);
        Comment comment = mapper.map(commentDtoRequest, Comment.class);

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapper.map(newComment, CommentDto.class);

//        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {

        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapper.map(comment, CommentDto.class)).collect(Collectors.toList());
//        return comment.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
        }

        return mapper.map(comment, CommentDto.class);
//        return mapToDto(comment);
    }

    @Override
    public CommentDto updateCommentById(Long postId, Long commentId, CommentDtoRequest commentDtoRequest) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
        }

        comment.setName(commentDtoRequest.getName());
        comment.setEmail(commentDtoRequest.getEmail());
        comment.setBody(commentDtoRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);

        return mapper.map(updatedComment, CommentDto.class);
    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to this post");
        }

        commentRepository.deleteById(commentId);
    }

}
