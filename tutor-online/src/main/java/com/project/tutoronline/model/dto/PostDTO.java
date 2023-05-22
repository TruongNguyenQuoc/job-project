package com.project.tutoronline.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {

    private long id;
    private String subject;
    private String address;
    private String price;
    private String numberOfSession;
    private String information;
    private String requirement;
    private String mode;
    private boolean status;

    private long timeTeachingId;
    private TimeTeachingDTO timeTeachingDTO;

    private long teachingId;
    private TeachingClassDTO teachingClassDTO;

}
