package com.project.tutoronline.service;

import com.project.tutoronline.model.dto.EmailAccountDTO;
import com.project.tutoronline.model.dto.EmailTemplateDTO;


public interface EmailService {

    boolean sendEmailForForgotPassword(EmailAccountDTO emailDTO);

    boolean sendEmailForRegister(EmailTemplateDTO emailTemplateDTO);

}
