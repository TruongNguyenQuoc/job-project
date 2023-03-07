package com.example.restapi.service;

import com.example.restapi.model.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(long id);

    Role findByName(String name);

}
