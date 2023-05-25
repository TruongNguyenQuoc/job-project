package com.project.tutoronline.repository;

import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.entity.PostTimeTeaching;
import com.project.tutoronline.model.entity.TimeTeaching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTimeTeachingRepository extends JpaRepository<PostTimeTeaching, Long> {

    List<PostTimeTeaching> findByPost(Post post);

    List<PostTimeTeaching> findByTimeTeaching(TimeTeaching timeTeaching);

}
