package com.project.tutoronline.model.mapper.impl;

import com.project.tutoronline.model.dto.CourseDTO;
import com.project.tutoronline.model.entity.Course;
import com.project.tutoronline.model.mapper.CourseMapper;
import com.project.tutoronline.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseMapperImpl implements CourseMapper {

    @Autowired
    private CourseService courseService;

    @Override
    public CourseDTO toDTO(Course course) {
        if(course == null) return null;

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setName(course.getName());
        courseDTO.setStatus(course.isStatus());

        return courseDTO;
    }

    @Override
    public List<CourseDTO> toListDTO(List<Course> coursees) {
        if (coursees == null) return null;

        List<CourseDTO> result = new ArrayList<>();
        coursees.forEach(course -> result.add(toDTO(course)));

        return result;
    }

    @Override
    public Course toEntity(CourseDTO courseDTO) {
        if(courseDTO == null) return null;

        Course course = courseService.findById(courseDTO.getId());
        if (course == null) course = new Course();
        course.setName(courseDTO.getName());
        course.setStatus(courseDTO.isStatus());

        return course;
    }
}
