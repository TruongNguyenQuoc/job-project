package com.project.tutoronline.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostTimeTeachingDTO {

    private long postId;
    private PostDTO postDTO;

    private long timeTeachingId;
    private TimeTeachingDTO timeTeachingDTO;

    private boolean status;
    
}
