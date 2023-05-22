package com.project.tutoronline.service;

import com.project.tutoronline.model.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findAll();

    Role findById(long id);

    Role findByName(String name);

}
