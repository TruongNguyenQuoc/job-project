package com.project.tutoronline.model.mapper;

import com.project.tutoronline.model.dto.JobDTO;
import com.project.tutoronline.model.entity.Job;

import java.util.List;

public interface JobMapper {

    JobDTO toDTO(Job job);

    List<JobDTO> toListDTO(List<Job> jobs);

    Job toEntity(JobDTO jobDTO);
    
}
