package com.project.tutoronline.service;

import com.project.tutoronline.model.entity.TeachingClass;

import java.util.List;

public interface TeachingClassService {

    List<TeachingClass> findAll();

    TeachingClass findById(long id);

    TeachingClass findByName(String name);

    TeachingClass save(TeachingClass teachingClass);
    
}
