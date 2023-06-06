package com.project.tutoronline.repository;

import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    Tutor findByAccount(Account account);

    @Query(value="SELECT * FROM tutor WHERE status = TRUE ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Tutor> findByRandom(int limit);

}
