package com.project.tutoronline.validator;

import com.project.tutoronline.model.dto.TimeTeachingDTO;
import com.project.tutoronline.model.entity.TeachingClass;
import com.project.tutoronline.service.TeachingClassService;
import com.project.tutoronline.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TimeTeachingValidator implements Validator {

    @Autowired
    private TeachingClassService teachingClassService;

    @Override
    public boolean supports(Class<?> clazz) {
        return TimeTeachingDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TimeTeachingDTO timeTeachingDTO = (TimeTeachingDTO) target;

        if (ValidatorUtil.isEmpty(timeTeachingDTO.getName())) {
            errors.rejectValue("name", "Tên Thời Gian Dạy không được để trống!",
                    "Tên Thời Gian Dạy không được để trống!");
        } else {
            TeachingClass teachingClass = teachingClassService.findByName(timeTeachingDTO.getName());
            if (teachingClass != null && teachingClass.getId() != timeTeachingDTO.getId()) {
                errors.rejectValue("name", "Tên Thời Gian Dạy đã tồn tại!",
                        "Tên Thời Gian Dạy đã tồn tại!");
            }
        }

    }
}
