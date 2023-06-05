package com.project.tutoronline.validator;

import com.project.tutoronline.model.dto.TutorDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.service.AccountService;
import com.project.tutoronline.service.TutorService;
import com.project.tutoronline.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TutorValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TutorService tutorService;

    @Override
    public boolean supports(Class<?> clazz) {
        return TutorDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TutorDTO tutorDTO = (TutorDTO) target;

        if (!ValidatorUtil.isNumeric(tutorDTO.getPhone())) {
            errors.rejectValue("phone", "Số Điện Thoại phải là số",
                    "Số Điện Thoại phải là số");
        } else if (!ValidatorUtil.checkPhone(tutorDTO.getPhone())) {
            errors.rejectValue("phone", "Số Điện Thoại phải có độ dài là 10!",
                    "Số Điện Thoại phải có độ dài là 10!");
        } else {
            Account account = accountService.findByPhone(tutorDTO.getPhone());
            if (account != null) {
                errors.rejectValue("phone", "Số Điện Thoại đã được đăng ký!",
                        "Số Điện Thoại đã được đăng ký!");
            }
        }

        Account account = accountService.findByEmail(tutorDTO.getAccountDTO().getEmail());
        if (account != null) {
            errors.rejectValue("accountDTO.email", "Địa Chỉ Email đã được đăng ký!",
                    "Địa Chỉ Email đã được đăng ký!");
        }

        if (!ValidatorUtil.isNumeric(tutorDTO.getYearCollege1())
                && !ValidatorUtil.isNumeric(tutorDTO.getYearCollege2())) {
            errors.rejectValue("yearCollege", "Niên Khóa phải là số",
                    "Niên Khóa phải là số");
        }

        if (!ValidatorUtil.isNumeric(tutorDTO.getIdNumber())) {
            errors.rejectValue("idNumber", "Số CMND/CCCD phải là số",
                    "Số CMND/CCCD phải là số");
        }

        if (tutorDTO.getAccountDTO().getPassword().length() < 6) {
            errors.rejectValue("accountDTO.password", "Mật Khẩu phải có độ dài lớn hơn 6",
                    "Mật Khẩu phải có độ dài lớn hơn 6");
        } else if (tutorDTO.getAccountDTO().getConfirmPassword().length() < 6) {
            errors.rejectValue("accountDTO.confirmPassword", "Xác Nhận Mật Khẩu phải có độ dài lớn hơn 6",
                    "Xác Nhận Mật Khẩu phải có độ dài lớn hơn 6");
        } else {
            if (!tutorDTO.getAccountDTO().getPassword()
                    .equalsIgnoreCase(tutorDTO.getAccountDTO().getConfirmPassword())) {
                errors.rejectValue("accountDTO.password", "Mật Khẩu không trùng khớp",
                        "Mật Khẩu không trùng khớp");
            }
        }
    }
}
