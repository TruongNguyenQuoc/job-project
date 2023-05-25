package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.entity.PostTimeTeaching;
import com.project.tutoronline.model.entity.TimeTeaching;
import com.project.tutoronline.repository.PostTimeTeachingRepository;
import com.project.tutoronline.service.PostTimeTeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTimeTeachingServiceImpl implements PostTimeTeachingService {

    @Autowired
    private PostTimeTeachingRepository postTimeTeachingRepository;

    @Override
    public List<PostTimeTeaching> findALl() {
        return postTimeTeachingRepository.findAll();
    }

    @Override
    public PostTimeTeaching findById(long id) {
        return postTimeTeachingRepository.findById(id).orElse(null);
    }

    @Override
    public List<PostTimeTeaching> findByPost(Post post) {
        return postTimeTeachingRepository.findByPost(post);
    }

    @Override
    public List<PostTimeTeaching> findByTimeTeaching(TimeTeaching timeTeaching) {
        return postTimeTeachingRepository.findByTimeTeaching(timeTeaching);
    }

    @Override
    public PostTimeTeaching save(PostTimeTeaching postTimeTeaching) {
        return postTimeTeachingRepository.save(postTimeTeaching);
    }
}
