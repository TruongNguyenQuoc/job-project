package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.repository.PostRepository;
import com.project.tutoronline.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<Post> findByRandom(int limit) {
        return postRepository.findByRandomAndProgress(limit, "Lớp Chưa Giao");
    }

    @Override
    public List<Post> findByAccount(Account account) {
        return postRepository.findByAccount(account);
    }

    @Override
    public Page<Post> findByPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public List<Post> searchPost(PostDTO postDTO) {
        String startDate = postDTO.getStartDate() + " 00:00:00";
        String endDate = postDTO.getEndDate() +  " 23:59:59";
        return postRepository.findByCreatedOn(startDate, endDate);
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }
}
