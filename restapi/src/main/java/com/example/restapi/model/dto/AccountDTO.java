package com.example.restapi.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {

    private long id;

    private long roleId;

    private RoleDTO roleDTO;

    private String username;

    private String password;

    private String fullName;

    private String email;

    private String phone;

    private String address;

    private String gender;

    private String birthDay;

    private String avatar;

    private String school;

    private boolean status;


    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
