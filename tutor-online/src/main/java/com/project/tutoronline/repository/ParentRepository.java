package com.project.tutoronline.repository;

import com.project.tutoronline.model.entity.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Long> {

    List<Parent> findByFullName(String fullName);

}
