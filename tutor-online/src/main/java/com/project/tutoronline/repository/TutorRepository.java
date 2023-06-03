package com.project.tutoronline.repository;

import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Tutor findByAccount(Account account);

}
