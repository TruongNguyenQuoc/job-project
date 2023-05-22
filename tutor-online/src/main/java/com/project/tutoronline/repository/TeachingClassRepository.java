package com.project.tutoronline.repository;

import com.project.tutoronline.model.entity.TeachingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachingClassRepository extends JpaRepository<TeachingClass, Long> {
}
