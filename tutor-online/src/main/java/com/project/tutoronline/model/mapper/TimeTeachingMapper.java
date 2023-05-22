package com.project.tutoronline.model.mapper;

import com.project.tutoronline.model.dto.TimeTeachingDTO;
import com.project.tutoronline.model.entity.TimeTeaching;

import java.util.List;

public interface TimeTeachingMapper {

    TimeTeachingDTO toDTO(TimeTeaching timeTeaching);

    List<TimeTeachingDTO> toListDTO(List<TimeTeaching> timeTeachings);

    TimeTeaching toEntity(TimeTeachingDTO timeTeachingDTO);
    
}
