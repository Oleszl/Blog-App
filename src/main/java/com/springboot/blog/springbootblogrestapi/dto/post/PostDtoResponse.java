package com.springboot.blog.springbootblogrestapi.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDtoResponse {

    private List<PostDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private long totalPages;
    private boolean last;

}
