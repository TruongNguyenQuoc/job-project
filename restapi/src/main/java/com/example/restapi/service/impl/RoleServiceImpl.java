package com.example.restapi.service.impl;

import com.example.restapi.model.entity.Role;
import com.example.restapi.repository.RoleRepository;
import com.example.restapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findById(long id) {
        return roleRepository.findById(id).orElse(new Role());
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElse(new Role());
    }
}
