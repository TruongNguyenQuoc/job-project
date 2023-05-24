package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.entity.TeachingClass;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.model.entity.TutorTeachingClass;
import com.project.tutoronline.repository.TutorTeachingClassRepository;
import com.project.tutoronline.service.TutorTeachingClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorTeachingClassServiceImpl implements TutorTeachingClassService {

    @Autowired
    private TutorTeachingClassRepository teachingClassRepository;

    @Override
    public List<TutorTeachingClass> findALl() {
        return teachingClassRepository.findAll();
    }

    @Override
    public TutorTeachingClass findById(long id) {
        return teachingClassRepository.findById(id).orElse(null);
    }

    @Override
    public List<TutorTeachingClass> findByTutor(Tutor tutor) {
        return teachingClassRepository.findByTutor(tutor);
    }

    @Override
    public List<TutorTeachingClass> findByTeachingClass(TeachingClass teachingClass) {
        return teachingClassRepository.findByTeachingClass(teachingClass);
    }

    @Override
    public TutorTeachingClass save(TutorTeachingClass tutorTeachingClass) {
        return teachingClassRepository.save(tutorTeachingClass);
    }
}
