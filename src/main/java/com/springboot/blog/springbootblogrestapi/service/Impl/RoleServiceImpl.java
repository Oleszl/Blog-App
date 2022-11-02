package com.springboot.blog.springbootblogrestapi.service.Impl;

import com.springboot.blog.springbootblogrestapi.entity.Role;
import com.springboot.blog.springbootblogrestapi.repository.RoleRepository;
import com.springboot.blog.springbootblogrestapi.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void createRoles() {

        List<Role> roles = roleRepository.findAll();

        if (roles.isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
        }
    }
}
