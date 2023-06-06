package com.project.tutoronline.repository;

import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAccount(Account account);

    @Query(value="SELECT * FROM post WHERE progress = :progress AND status = TRUE ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Post> findByRandomAndProgress(int limit, String progress);

    @Query(value = "SELECT * FROM post WHERE created_on BETWEEN :startDate AND :endDate ORDER BY created_on DESC",
            nativeQuery = true)
    List<Post> findByCreatedOn(String startDate, String endDate);

}
