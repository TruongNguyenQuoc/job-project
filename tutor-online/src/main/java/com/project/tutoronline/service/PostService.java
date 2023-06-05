package com.project.tutoronline.service;

import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    Post findById(long id);

    List<Post> findByAccount(Account account);

    Page<Post> findByPage(Pageable pageable);

    List<Post> searchPost(PostDTO postDTO);

    Post save(Post post);

}
