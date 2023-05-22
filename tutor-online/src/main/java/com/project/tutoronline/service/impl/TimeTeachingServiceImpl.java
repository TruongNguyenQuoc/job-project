package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.entity.TimeTeaching;
import com.project.tutoronline.repository.TeachingClassRepository;
import com.project.tutoronline.repository.TimeTeachingRepository;
import com.project.tutoronline.service.TimeTeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeTeachingServiceImpl implements TimeTeachingService {

    @Autowired
    private TimeTeachingRepository timeTeachingRepository;

    @Override
    public List<TimeTeaching> findAll() {
        return timeTeachingRepository.findAll();
    }

    @Override
    public TimeTeaching findById(long id) {
        return timeTeachingRepository.findById(id).orElse(null);
    }

    @Override
    public TimeTeaching save(TimeTeaching timeTeaching) {
        return timeTeachingRepository.save(timeTeaching);
    }
}
