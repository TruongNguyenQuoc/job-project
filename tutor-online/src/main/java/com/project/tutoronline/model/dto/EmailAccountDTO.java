package com.project.tutoronline.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAccountDTO {

    // Account
    private long id;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String phone;

    // custom
    private String otp;
    private String contractId;
    private String tmt;
    private String accountRef;
    private String link;

}
