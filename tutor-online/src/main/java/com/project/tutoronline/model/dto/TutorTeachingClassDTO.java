package com.project.tutoronline.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TutorTeachingClassDTO {

    private long tutorId;
    private TutorDTO tutorDTO;

    private long teachingClassId;
    private TeachingClassDTO teachingClassDTO;

    private boolean status;

}
