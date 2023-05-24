package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.entity.Course;
import com.project.tutoronline.repository.CourseRepository;
import com.project.tutoronline.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(long id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course findByName(String name) {
        return courseRepository.findByName(name);
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }
}
