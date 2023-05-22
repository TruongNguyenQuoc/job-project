package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.repository.TutorRepository;
import com.project.tutoronline.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorServiceImpl implements TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public List<Tutor> findAll() {
        return tutorRepository.findAll();
    }

    @Override
    public Tutor findById(long id) {
        return tutorRepository.findById(id).orElse(null);
    }

    @Override
    public Tutor save(Tutor tutor) {
        return tutorRepository.save(tutor);
    }
}
