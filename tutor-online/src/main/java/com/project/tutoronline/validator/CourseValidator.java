package com.project.tutoronline.validator;

import com.project.tutoronline.model.dto.CourseDTO;
import com.project.tutoronline.model.entity.Course;
import com.project.tutoronline.service.CourseService;
import com.project.tutoronline.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Controller
public class CourseValidator implements Validator {

    @Autowired
    private CourseService courseService;

    @Override
    public boolean supports(Class<?> clazz) {
        return CourseDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CourseDTO courseDTO = (CourseDTO) target;

        if (ValidatorUtil.isEmpty(courseDTO.getName())) {
            errors.rejectValue("name", "Tên Môn Học không được để trống!",
                    "Tên Môn Học không được để trống!");
        } else {
            Course course = courseService.findByName(courseDTO.getName());
            if (course != null && course.getId() != courseDTO.getId()) {
                errors.rejectValue("name", "Tên Môn Học đã tồn tại!",
                        "Tên Môn Học đã tồn tại!");
            }
        }
    }
}
