package com.project.tutoronline.model.mapper;

import com.project.tutoronline.model.dto.TutorDTO;
import com.project.tutoronline.model.entity.Tutor;

import java.util.List;

public interface TutorMapper {

    TutorDTO toDTO(Tutor tutor);

    List<TutorDTO> toListDTO(List<Tutor> tutors);

    Tutor toEntity(TutorDTO tutorDTO);
    
}
