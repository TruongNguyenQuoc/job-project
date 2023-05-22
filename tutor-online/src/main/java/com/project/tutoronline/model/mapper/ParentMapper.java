package com.project.tutoronline.model.mapper;

import com.project.tutoronline.model.dto.ParentDTO;
import com.project.tutoronline.model.entity.Parent;

import java.util.List;

public interface ParentMapper {

    ParentDTO toDTO(Parent parent);

    List<ParentDTO> toListDTO(List<Parent> parents);

    Parent toEntity(ParentDTO parentDTO);
    
}
