package com.project.tutoronline.model.mapper.impl;

import com.project.tutoronline.model.dto.TeachingClassDTO;
import com.project.tutoronline.model.entity.TeachingClass;
import com.project.tutoronline.model.mapper.TeachingClassMapper;
import com.project.tutoronline.service.TeachingClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeachingClassMapperImpl implements TeachingClassMapper {

    @Autowired
    private TeachingClassService teachingClassService;

    @Override
    public TeachingClassDTO toDTO(TeachingClass teachingClass) {
        if(teachingClass == null) return null;

        TeachingClassDTO teachingClassDTO = new TeachingClassDTO();
        teachingClassDTO.setId(teachingClass.getId());
        teachingClassDTO.setName(teachingClass.getName());
        teachingClassDTO.setStatus(teachingClass.isStatus());

        return teachingClassDTO;
    }

    @Override
    public List<TeachingClassDTO> toListDTO(List<TeachingClass> teachingClasses) {
        if (teachingClasses == null) return null;

        List<TeachingClassDTO> result = new ArrayList<>();
        teachingClasses.forEach(teachingClass -> result.add(toDTO(teachingClass)));

        return result;
    }

    @Override
    public TeachingClass toEntity(TeachingClassDTO teachingClassDTO) {
        if(teachingClassDTO == null) return null;

        TeachingClass teachingClass = teachingClassService.findById(teachingClassDTO.getId());
        if (teachingClass == null) teachingClass = new TeachingClass();
        teachingClass.setName(teachingClassDTO.getName());
        teachingClass.setStatus(teachingClassDTO.isStatus());

        return teachingClass;
    }
}
