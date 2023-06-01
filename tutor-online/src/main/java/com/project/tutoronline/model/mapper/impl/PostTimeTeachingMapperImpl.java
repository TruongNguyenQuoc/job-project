package com.project.tutoronline.model.mapper.impl;

import com.project.tutoronline.model.dto.PostTimeTeachingDTO;
import com.project.tutoronline.model.entity.PostTimeTeaching;
import com.project.tutoronline.model.entity.PostTimeTeachingId;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.model.mapper.PostTimeTeachingMapper;
import com.project.tutoronline.model.mapper.TimeTeachingMapper;
import com.project.tutoronline.service.PostService;
import com.project.tutoronline.service.TimeTeachingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostTimeTeachingMapperImpl implements PostTimeTeachingMapper {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private TimeTeachingService timeTeachingService;

    @Autowired
    private TimeTeachingMapper timeTeachingMapper;

    @Override
    public PostTimeTeachingDTO toDTO(PostTimeTeaching postTimeTeaching) {
        if (postTimeTeaching == null) return null;

        PostTimeTeachingDTO postTimeTeachingDTO = new PostTimeTeachingDTO();
        postTimeTeachingDTO.setPostId(postTimeTeaching.getKeyId().getPostId());
        postTimeTeachingDTO.setTimeTeachingId(postTimeTeaching.getKeyId().getTimeTeachingId());
        postTimeTeachingDTO.setTimeTeachingDTO(timeTeachingMapper.toDTO(postTimeTeaching.getTimeTeaching()));
        postTimeTeachingDTO.setPostDTO(postMapper.toDTO(postTimeTeaching.getPost()));
        postTimeTeachingDTO.setStatus(postTimeTeaching.isStatus());

        return postTimeTeachingDTO;
    }

    @Override
    public List<PostTimeTeachingDTO> toListDTO(List<PostTimeTeaching> postTimeTeachings) {
        if (postTimeTeachings == null) return null;

        List<PostTimeTeachingDTO> result = new ArrayList<>();
        postTimeTeachings.forEach(postTimeTeaching -> result.add(toDTO(postTimeTeaching)));

        return result;
    }

    @Override
    public PostTimeTeaching toEntity(PostTimeTeachingDTO postTimeTeachingDTO) {
        if (postTimeTeachingDTO == null) return null;

        PostTimeTeaching postTimeTeaching = new PostTimeTeaching();
        PostTimeTeachingId id = new PostTimeTeachingId();
        id.setPostId(postTimeTeachingDTO.getPostId());
        id.setTimeTeachingId(postTimeTeachingDTO.getTimeTeachingId());

        postTimeTeaching.setKeyId(id);
        postTimeTeaching.setPost(postService.findById(postTimeTeachingDTO.getPostId()));
        postTimeTeaching.setTimeTeaching(timeTeachingService.findById(postTimeTeachingDTO.getTimeTeachingId()));
        postTimeTeaching.setStatus(postTimeTeachingDTO.isStatus());

        return postTimeTeaching;
    }
}
