package com.project.tutoronline.validator;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.service.AccountService;
import com.project.tutoronline.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            AccountDTO accountDTO = (AccountDTO) target;
            Account account = null;

            // verify username
            String username = accountDTO.getUsername();
            if (ValidatorUtil.isEmpty(username)) {
                errors.rejectValue("username", "Vui lòng nhập Tên Đăng Nhập!",
                        "Vui lòng nhập Tên Đăng Nhập!");
            } else {
                account = accountService.findByUsername(username.trim());
                if (account != null && account.getUsername().equals(accountDTO.getUsername())) {
                    errors.rejectValue("username", "Tên Đăng Nhập đã được đăng ký!",
                            "Tên Đăng Nhập đã được đăng ký!");
                }
            }

            // verify password
            if (ValidatorUtil.isEmpty(accountDTO.getPassword())) {
                errors.rejectValue("password", "Vui lòng nhập Mật Khẩu!",
                        "Vui lòng nhập Mật Khẩu!");
            } else {
                if (accountDTO.getPassword().length() < 6) {
                    errors.rejectValue("password", "Vui lòng nhập Mật Khẩu lớn hơn 6 ký tự!",
                            "Vui lòng nhập Mật Khẩu lớn hơn 6 ký tự!");
                }
            }
        } catch (Exception e) {
            errors.rejectValue("msg", "Có lỗi xảy ra, vui lòng thử lại!",
                    "Có lỗi xảy ra, vui lòng thử lại!");
        }
    }

}
