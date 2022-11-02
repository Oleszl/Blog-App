package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.constant.HttpStatuses;
import com.springboot.blog.springbootblogrestapi.dto.comment.CommentDto;
import com.springboot.blog.springbootblogrestapi.dto.comment.CommentDtoRequest;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;


    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "Create Comment REST API")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HttpStatuses.CREATED),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
    })
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long id,
                                                    @Valid @RequestBody CommentDtoRequest commentDtoRequest) {
        return new ResponseEntity<>(commentService.createComment(id, commentDtoRequest), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get All Comments By Post ID REST API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
    })
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @ApiOperation(value = "Get Single Comment By ID REST API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
    })
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") Long postId,
                                                     @PathVariable("commentId") Long commentId) {

        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @ApiOperation(value = "Update Comment By ID REST API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
    })
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable("postId") Long postId,
                                                        @PathVariable("commentId") Long commentId,
                                                        @Valid @RequestBody CommentDtoRequest commentDtoRequest) {

        return new ResponseEntity<>(commentService.updateCommentById(postId, commentId, commentDtoRequest), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Comment By ID REST API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
    })
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("postId") Long postId,
                                                    @PathVariable("commentId") Long commentId) {

        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
