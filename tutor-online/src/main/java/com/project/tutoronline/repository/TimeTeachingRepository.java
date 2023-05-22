package com.project.tutoronline.repository;

import com.project.tutoronline.model.entity.TimeTeaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTeachingRepository extends JpaRepository<TimeTeaching, Long> {
}
