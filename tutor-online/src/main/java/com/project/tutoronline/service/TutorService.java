package com.project.tutoronline.service;

import com.project.tutoronline.model.entity.Tutor;

import java.util.List;

public interface TutorService {

    List<Tutor> findAll();

    Tutor findById(long id);

    Tutor save(Tutor tutor);
    
}
