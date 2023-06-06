package com.project.tutoronline.service;

import com.project.tutoronline.model.entity.TeachingClass;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.model.entity.TutorTeachingClass;

import java.util.List;

public interface TutorTeachingClassService {

    List<TutorTeachingClass> findALl();

    TutorTeachingClass findById(long id);

    List<TutorTeachingClass> findByTutor(Tutor tutor);

    List<TutorTeachingClass> findByTeachingClass(TeachingClass teachingClass);

    TutorTeachingClass save(TutorTeachingClass tutorTeachingClass);

    void delete(TutorTeachingClass tutorTeachingClass);

}
