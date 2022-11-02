package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.constant.HttpStatuses;
import com.springboot.blog.springbootblogrestapi.dto.JwtAuthResponse;
import com.springboot.blog.springbootblogrestapi.dto.auth.LoginDto;
import com.springboot.blog.springbootblogrestapi.dto.auth.SignUpDto;
import com.springboot.blog.springbootblogrestapi.security.JwtTokenProvider;
import com.springboot.blog.springbootblogrestapi.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Auth controller exposes siginin and signup REST APIs")
@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private UserService userService;

    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @ApiOperation(value = "REST API to Signin or Login user to Blog app")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = HttpStatuses.OK),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
    })
    @PostMapping("/auth/signin")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token from tokenProvider
        String token = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @ApiOperation(value = "REST API to Register or Signup user to Blog app")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = HttpStatuses.CREATED),
            @ApiResponse(code = 400, message = HttpStatuses.BAD_REQUEST),
    })
    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

        userService.createUser(signUpDto);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

    }
}
