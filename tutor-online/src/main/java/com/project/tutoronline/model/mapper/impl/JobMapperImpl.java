package com.project.tutoronline.model.mapper.impl;

import com.project.tutoronline.model.dto.JobDTO;
import com.project.tutoronline.model.entity.Job;
import com.project.tutoronline.model.mapper.JobMapper;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.model.mapper.TutorMapper;
import com.project.tutoronline.service.JobService;
import com.project.tutoronline.service.PostService;
import com.project.tutoronline.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JobMapperImpl implements JobMapper {

    @Autowired
    private JobService jobService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorMapper tutorMapper;

    @Override
    public JobDTO toDTO(Job job) {
        if (job == null) return null;

        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setStatus(job.isStatus());

        if (job.getPost() != null) {
            jobDTO.setPostId(job.getPost().getId());
            jobDTO.setPostDTO(postMapper.toDTO(job.getPost()));
        }

        if (job.getTutor() != null) {
            jobDTO.setTutorId(job.getTutor().getId());
            jobDTO.setTutorDTO(tutorMapper.toDTO(job.getTutor()));
        }

        return jobDTO;
    }

    @Override
    public List<JobDTO> toListDTO(List<Job> jobs) {
        if (jobs == null) return null;

        List<JobDTO> result = new ArrayList<>();
        jobs.forEach(job -> result.add(toDTO(job)));

        return result;
    }

    @Override
    public Job toEntity(JobDTO jobDTO) {
        if (jobDTO == null) return null;

        Job job = jobService.findById(jobDTO.getId());

        if (job == null) {
            job = new Job();
        }
        job.setStatus(jobDTO.isStatus());

        job.setPost(postService.findById(jobDTO.getPostId()));
        job.setTutor(tutorService.findById(jobDTO.getTutorId()));

        return job;
    }
}
