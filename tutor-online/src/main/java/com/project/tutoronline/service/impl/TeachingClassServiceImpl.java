package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.entity.TeachingClass;
import com.project.tutoronline.repository.TeachingClassRepository;
import com.project.tutoronline.service.TeachingClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachingClassServiceImpl implements TeachingClassService {

    @Autowired
    private TeachingClassRepository teachingClassRepository;

    @Override
    public List<TeachingClass> findAll() {
        return teachingClassRepository.findAll();
    }

    @Override
    public TeachingClass findById(long id) {
        return teachingClassRepository.findById(id).orElse(null);
    }

    @Override
    public TeachingClass save(TeachingClass teachingClass) {
        return teachingClassRepository.save(teachingClass);
    }
}
