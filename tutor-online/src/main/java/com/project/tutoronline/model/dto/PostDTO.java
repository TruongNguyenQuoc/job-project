package com.project.tutoronline.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDTO {

    private long id;
    private String fullName;
    private String address;
    private String price;
    private String numberOfSession;
    private String information;
    private String requirement;
    private String mode;
    private String progress;
    private boolean status;

    private long accountId;
    private AccountDTO accountDTO;

    private long teachingClassId;
    private TeachingClassDTO teachingClassDTO;

    private long courseId;
    private CourseDTO courseDTO;

    private List<String> timeTeachingId;

    private String startDate;
    private String endDate;

}
