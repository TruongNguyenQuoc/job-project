package com.project.tutoronline.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class TutorDTO {

    private long id;
    private String fullName;
    private String phone;
    private String avatar; //
    private String address;
    private String birthday;
    private String origin;
    private String idNumber;
    private String idPhoto;//
    private String cardPhoto;//
    private String degreePhoto;//
    private String school;
    private String specialization;
    private String yearCollege;
    private String level;
    private String advantage;
    private boolean status;

    private MultipartFile avatarMul;
    private MultipartFile idPhotoMul;
    private MultipartFile cardPhotoMul;
    private MultipartFile degreePhotoMul;

    private AccountDTO accountDTO;
    private long accountId;

    private List<String> teachingClassIdList;

    private String yearCollege1;
    private String yearCollege2;
}
