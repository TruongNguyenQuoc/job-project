package com.project.tutoronline.service;

import com.project.tutoronline.model.entity.*;

import java.util.List;

public interface PostTimeTeachingService {

    List<PostTimeTeaching> findALl();

    PostTimeTeaching findById(long id);

    List<PostTimeTeaching> findByPost(Post post);

    List<PostTimeTeaching> findByTimeTeaching(TimeTeaching timeTeaching);

    PostTimeTeaching save(PostTimeTeaching postTimeTeaching);
    
}
