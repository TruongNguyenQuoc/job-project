package com.project.tutoronline.service;

import com.project.tutoronline.model.dto.TutorDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Tutor;

import java.util.List;

public interface TutorService {

    List<Tutor> findAll();

    Tutor findById(long id);

    Tutor save(Tutor tutor);

    Tutor findByAccount(Account account);

    Tutor registerTutor(Tutor tutor, TutorDTO tutorDTO);
}
