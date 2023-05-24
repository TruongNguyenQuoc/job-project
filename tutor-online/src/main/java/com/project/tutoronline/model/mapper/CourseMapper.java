package com.project.tutoronline.model.mapper;

import com.project.tutoronline.model.dto.CourseDTO;
import com.project.tutoronline.model.entity.Course;

import java.util.List;

public interface CourseMapper {

    CourseDTO toDTO(Course course);

    List<CourseDTO> toListDTO(List<Course> courses);

    Course toEntity(CourseDTO courseDTO);
    
}
