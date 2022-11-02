package com.springboot.blog.springbootblogrestapi.service.Impl;

import com.springboot.blog.springbootblogrestapi.dto.post.PostDto;
import com.springboot.blog.springbootblogrestapi.dto.post.PostDtoRequest;
import com.springboot.blog.springbootblogrestapi.dto.post.PostDtoResponse;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.exceptions.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.repository.PostRepository;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDtoRequest postRequest) {
        Post postToSave = mapper.map(postRequest, Post.class);

        return mapper.map(postRepository.save(postToSave), PostDto.class);
    }

    @Override
    public PostDtoResponse getAllPosts(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPost = posts.getContent();

        List<PostDto> contents = listOfPost.stream().map(content -> mapper.map(content, PostDto.class)).collect(Collectors.toList());

        PostDtoResponse postResponse = new PostDtoResponse();

        postResponse.setContent(contents);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        return mapper.map(postRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException
                                        ("Post", "id", id)),
                PostDto.class);
    }


    @Override
    public PostDto updatePost(PostDtoRequest postRequest, Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setContent(postRequest.getContent());

        return mapper.map(postRepository.save(post), PostDto.class);


    }

    @Override
    public void deleteById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        postRepository.deleteById(id);
    }

}
