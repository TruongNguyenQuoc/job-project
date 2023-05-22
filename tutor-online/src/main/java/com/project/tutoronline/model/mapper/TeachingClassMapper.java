package com.project.tutoronline.model.mapper;

import com.project.tutoronline.model.dto.TeachingClassDTO;
import com.project.tutoronline.model.entity.TeachingClass;

import java.util.List;

public interface TeachingClassMapper {

    TeachingClassDTO toDTO(TeachingClass teachingClass);

    List<TeachingClassDTO> toListDTO(List<TeachingClass> teachingClasses);

    TeachingClass toEntity(TeachingClassDTO teachingClassDTO);
    
}
