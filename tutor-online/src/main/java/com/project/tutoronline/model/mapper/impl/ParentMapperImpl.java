package com.project.tutoronline.model.mapper.impl;

import com.project.tutoronline.model.dto.ParentDTO;
import com.project.tutoronline.model.entity.Parent;
import com.project.tutoronline.model.mapper.ParentMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParentMapperImpl implements ParentMapper {



    @Override
    public ParentDTO toDTO(Parent parent) {
        return null;
    }

    @Override
    public List<ParentDTO> toListDTO(List<Parent> parents) {
        return null;
    }

    @Override
    public Parent toEntity(ParentDTO parentDTO) {
        return null;
    }
}
