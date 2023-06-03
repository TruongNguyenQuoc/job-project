package com.project.tutoronline.validator;

import com.project.tutoronline.model.dto.ParentDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Parent;
import com.project.tutoronline.service.AccountService;
import com.project.tutoronline.service.ParentService;
import com.project.tutoronline.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ParentValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ParentDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ParentDTO parentDTO = (ParentDTO) target;

        if (!ValidatorUtil.checkPhone(parentDTO.getPhone())) {
            errors.rejectValue("phone", "Số Điện Thoại không đúng định dạng",
                    "Số Điện Thoại không đúng định dạng");
        } else {
            Account account = accountService.findByPhone(parentDTO.getPhone());
            if (account != null) {
                errors.rejectValue("phone", "Số Điện Thoại đã được đăng ký",
                        "Số Điện Thoại đã được đăng ký");
            }
        }

        Account account = accountService.findByEmail(parentDTO.getAccountDTO().getEmail());
        if (account != null) {
            errors.rejectValue("accountDTO.email", "Địa Chỉ Email đã được đăng ký!",
                    "Địa Chỉ Email đã được đăng ký!");
        }

        if (parentDTO.getAccountDTO().getPassword().length() < 6) {
            errors.rejectValue("accountDTO.password", "Mật Khẩu phải có độ dài lớn hơn 6",
                    "Mật Khẩu phải có độ dài lớn hơn 6");
        } else if (parentDTO.getAccountDTO().getConfirmPassword().length() < 6) {
            errors.rejectValue("accountDTO.confirmPassword", "Xác Nhận Mật Khẩu phải có độ dài lớn hơn 6",
                    "Xác Nhận Mật Khẩu phải có độ dài lớn hơn 6");
        } else {
            if (!parentDTO.getAccountDTO().getPassword()
                    .equalsIgnoreCase(parentDTO.getAccountDTO().getConfirmPassword())) {
                errors.rejectValue("accountDTO.password", "Mật Khẩu không trùng khớp",
                        "Mật Khẩu không trùng khớp");
            }
        }
    }
}
