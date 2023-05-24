package com.project.tutoronline.service;

import com.project.tutoronline.model.entity.Course;

import java.util.List;

public interface CourseService {

    List<Course> findAll();

    Course findById(long id);

    Course findByName(String name);

    Course save(Course course);
    
}
