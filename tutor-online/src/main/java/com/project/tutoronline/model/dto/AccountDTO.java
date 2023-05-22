package com.project.tutoronline.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    private long id;
    private String role;
    private String username;
    private String password;
    private String email;
    private boolean status;

    private RoleDTO roleDTO;

    private ParentDTO parentDTO;
    private long parentId;

    private TutorDTO tutorDTO;
    private long tutorId;

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
