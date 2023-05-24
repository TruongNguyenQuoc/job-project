package com.project.tutoronline.model.mapper.impl;

import com.project.tutoronline.model.dto.TimeTeachingDTO;
import com.project.tutoronline.model.entity.TeachingClass;
import com.project.tutoronline.model.entity.TimeTeaching;
import com.project.tutoronline.model.mapper.TimeTeachingMapper;
import com.project.tutoronline.service.TimeTeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TimeTeachingMapperImpl implements TimeTeachingMapper {

    @Autowired
    private TimeTeachingService timeTeachingService;

    @Override
    public TimeTeachingDTO toDTO(TimeTeaching timeTeaching) {
        if(timeTeaching == null) return null;

        TimeTeachingDTO timeTeachingDTO = new TimeTeachingDTO();
        timeTeachingDTO.setId(timeTeaching.getId());
        timeTeachingDTO.setName(timeTeaching.getName());
        timeTeachingDTO.setStatus(timeTeaching.isStatus());

        return timeTeachingDTO;
    }

    @Override
    public List<TimeTeachingDTO> toListDTO(List<TimeTeaching> timeTeachings) {
        if (timeTeachings == null) return null;

        List<TimeTeachingDTO> result = new ArrayList<>();
        timeTeachings.forEach(timeTeaching -> result.add(toDTO(timeTeaching)));

        return result;
    }

    @Override
    public TimeTeaching toEntity(TimeTeachingDTO timeTeachingDTO) {
        if(timeTeachingDTO == null) return null;

        TimeTeaching timeTeaching = timeTeachingService.findById(timeTeachingDTO.getId());
        if (timeTeaching == null) timeTeaching = new TimeTeaching();
        timeTeaching.setName(timeTeachingDTO.getName());
        timeTeaching.setStatus(timeTeachingDTO.isStatus());

        return timeTeaching;
    }
}
