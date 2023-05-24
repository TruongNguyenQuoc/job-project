package com.project.tutoronline.repository;

import com.project.tutoronline.model.entity.TeachingClass;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.model.entity.TutorTeachingClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorTeachingClassRepository extends JpaRepository<TutorTeachingClass, Long> {

    List<TutorTeachingClass> findByTutor(Tutor tutor);

    List<TutorTeachingClass> findByTeachingClass(TeachingClass teachingClass);

}
