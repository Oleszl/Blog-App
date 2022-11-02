package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.constant.AppConstants;
import com.springboot.blog.springbootblogrestapi.constant.HttpStatuses;
import com.springboot.blog.springbootblogrestapi.dto.post.PostDto;
import com.springboot.blog.springbootblogrestapi.dto.post.PostDtoRequest;
import com.springboot.blog.springbootblogrestapi.dto.post.PostDtoResponse;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "CRUD Rest APIs for Post resources")
@RestController
@RequestMapping("/api/v1")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation(value = "Create Post REST API")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HttpStatuses.CREATED),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDtoRequest postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get All Posts REST API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
    })
    @GetMapping("/posts")
    public PostDtoResponse getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {

        return postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
    }

    @ApiOperation(value = "Get Post By Id REST API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
    })
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @ApiOperation(value = "Update Post By Id REST API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)

    })
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDtoRequest postRequest, @PathVariable(name = "id") Long id) {

        PostDto postResponse = postService.updatePost(postRequest, id);

        return new ResponseEntity<>(postResponse, HttpStatus.OK);

    }

    @ApiOperation(value = "Delete Post By Id REST API")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
            @ApiResponse(code = 401, message = HttpStatuses.UNAUTHORIZED),
            @ApiResponse(code = 403, message = HttpStatuses.FORBIDDEN)
    })
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {

        postService.deleteById(id);
        return new ResponseEntity<>("Post Entity deleted successfully", HttpStatus.OK);
    }

}
