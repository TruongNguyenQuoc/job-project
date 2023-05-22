package com.project.tutoronline.repository;

import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAccount(Account account);

}