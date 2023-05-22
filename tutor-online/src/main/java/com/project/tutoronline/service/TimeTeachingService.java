package com.project.tutoronline.service;

import com.project.tutoronline.model.entity.TimeTeaching;

import java.util.List;

public interface TimeTeachingService {

    List<TimeTeaching> findAll();

    TimeTeaching findById(long id);

    TimeTeaching save(TimeTeaching timeTeaching);

}
