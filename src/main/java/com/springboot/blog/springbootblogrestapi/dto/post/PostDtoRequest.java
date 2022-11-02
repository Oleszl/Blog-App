package com.springboot.blog.springbootblogrestapi.dto.post;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@ApiModel(description = "Post model information")
@Data
public class PostDtoRequest {

    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;
}
