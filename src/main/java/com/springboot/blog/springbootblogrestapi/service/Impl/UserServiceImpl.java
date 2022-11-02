package com.springboot.blog.springbootblogrestapi.service.Impl;

import com.springboot.blog.springbootblogrestapi.dto.auth.SignUpDto;
import com.springboot.blog.springbootblogrestapi.entity.Role;
import com.springboot.blog.springbootblogrestapi.entity.User;
import com.springboot.blog.springbootblogrestapi.exception.exceptions.EmailExistException;
import com.springboot.blog.springbootblogrestapi.exception.exceptions.UsernameExistException;
import com.springboot.blog.springbootblogrestapi.repository.RoleRepository;
import com.springboot.blog.springbootblogrestapi.repository.UserRepository;
import com.springboot.blog.springbootblogrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void createUser(SignUpDto signUpDto) {

        if (userRepository.existsByUsername(signUpDto.getUsername())) {
            throw new UsernameExistException(signUpDto.getUsername());
        }

        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            throw new EmailExistException(signUpDto.getEmail());
        }

        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();

        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);


    }
}
