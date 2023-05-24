package com.project.tutoronline.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class TutorTeachingClass {

    @EmbeddedId
    private TutorTeachingClassId keyId;

    @ManyToOne
    @MapsId("tutorId")
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @ManyToOne
    @MapsId("teachingClassId")
    @JoinColumn(name = "teaching_class_id")
    private TeachingClass teachingClass;

    private boolean status;

}
