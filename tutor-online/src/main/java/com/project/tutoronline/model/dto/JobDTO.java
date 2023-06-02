package com.project.tutoronline.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobDTO {

    private long id;
    private boolean status;

    private long postId;
    private PostDTO postDTO;

    private long tutorId;
    private TutorDTO tutorDTO;

}
