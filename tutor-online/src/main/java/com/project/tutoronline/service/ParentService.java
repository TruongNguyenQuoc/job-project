package com.project.tutoronline.service;

import com.project.tutoronline.model.entity.Parent;

import java.util.List;

public interface ParentService {

    List<Parent> findAll();

    Parent findById(long id);

    Parent save(Parent parent);
}
