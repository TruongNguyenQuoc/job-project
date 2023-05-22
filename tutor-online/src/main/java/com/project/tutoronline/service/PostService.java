package com.project.tutoronline.service;

import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Post;

import java.util.List;

public interface PostService {

    List<Post> findAll();

    Post findById(long id);

    List<Post> findByAccount(Account account);

    Post save(Post post);

}
