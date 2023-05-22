package com.project.tutoronline.model.mapper;

import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.model.entity.Post;

import java.util.List;

public interface PostMapper {

    PostDTO toDTO(Post post);

    List<PostDTO> toListDTO(List<Post> posts);

    Post toEntity(PostDTO postDTO);
    
}
