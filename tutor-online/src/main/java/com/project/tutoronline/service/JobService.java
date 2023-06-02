package com.project.tutoronline.service;

import com.project.tutoronline.model.entity.Job;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.entity.Tutor;

import java.util.List;

public interface JobService {

    List<Job> findAll();

    Job findById(long id);

    Job findByPost(Post post);

    List<Job> findByTutor(Tutor tutor);

    Job save(Job job);
}
