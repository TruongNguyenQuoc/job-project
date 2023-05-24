package com.project.tutoronline.validator;

import com.project.tutoronline.model.dto.TeachingClassDTO;
import com.project.tutoronline.model.entity.TeachingClass;
import com.project.tutoronline.service.TeachingClassService;
import com.project.tutoronline.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TeachingClassValidator implements Validator {

    @Autowired
    private TeachingClassService teachingClassService;

    @Override
    public boolean supports(Class<?> clazz) {
        return TeachingClassDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TeachingClassDTO teachingClassDTO = (TeachingClassDTO) target;

        if (ValidatorUtil.isEmpty(teachingClassDTO.getName())) {
            errors.rejectValue("name", "Tên Lớp Dạy không được để trống!",
                    "Tên Lớp Dạy không được để trống!");
        } else {
            TeachingClass teachingClass = teachingClassService.findByName(teachingClassDTO.getName());
            if (teachingClass != null && teachingClass.getId() != teachingClassDTO.getId()) {
                errors.rejectValue("name", "Tên Lớp Dạy đã tồn tại!",
                        "Tên Lớp Dạy đã tồn tại!");
            }
        }
    }
}
