package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.dto.post.PostDto;
import com.springboot.blog.springbootblogrestapi.dto.post.PostDtoRequest;
import com.springboot.blog.springbootblogrestapi.dto.post.PostDtoResponse;

public interface PostService {

    PostDto createPost(PostDtoRequest postRequest);

    PostDtoResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDtoRequest postRequest, Long id);

    void deleteById(Long id);
}
