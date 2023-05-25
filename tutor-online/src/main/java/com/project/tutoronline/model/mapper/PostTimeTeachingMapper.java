package com.project.tutoronline.model.mapper;

import com.project.tutoronline.model.dto.PostTimeTeachingDTO;
import com.project.tutoronline.model.entity.PostTimeTeaching;

import java.util.List;

public interface PostTimeTeachingMapper {

    PostTimeTeachingDTO toDTO(PostTimeTeaching postTimeTeaching);

    List<PostTimeTeachingDTO> toListDTO(List<PostTimeTeaching> postTimeTeachings);

    PostTimeTeaching toEntity(PostTimeTeachingDTO postTimeTeachingDTO);
    
}
