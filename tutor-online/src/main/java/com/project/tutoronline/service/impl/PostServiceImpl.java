package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.repository.PostRepository;
import com.project.tutoronline.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post findById(long id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public List<Post> findByAccount(Account account) {
        return postRepository.findByAccount(account);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }
}
