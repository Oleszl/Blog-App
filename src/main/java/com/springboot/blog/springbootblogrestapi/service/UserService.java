package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.dto.auth.SignUpDto;

public interface UserService {

    void createUser(SignUpDto signUpDto);
}
