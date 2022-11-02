package com.springboot.blog.springbootblogrestapi.dto.auth;

import lombok.Data;

@Data
public class LoginDto {

    private String usernameOrEmail;

    private String password;
}
