package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.entity.Job;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.repository.JobRepository;
import com.project.tutoronline.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job findById(long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public Job findByPost(Post post) {
        return jobRepository.findByPost(post);
    }

    @Override
    public List<Job> findByTutor(Tutor tutor) {
        return jobRepository.findByTutor(tutor);
    }

    @Override
    public Job save(Job job) {
        return jobRepository.save(job);
    }
}
