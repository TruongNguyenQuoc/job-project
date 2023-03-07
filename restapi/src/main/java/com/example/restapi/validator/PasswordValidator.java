package com.example.restapi.validator;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {

    private static final int SIZE_PASSWORD = 8;

    @Autowired
    private AccountService accountService;

    //    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        try {
            AccountDTO accountDTO = (AccountDTO) target;
            checkOldPassword(accountDTO, errors);
            checkNewPassword(accountDTO, errors);
            checkConfirmPassword(accountDTO, errors);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkOldPassword(AccountDTO accountDTO, Errors errors) {
        if (isBlankPassword(accountDTO.getOldPassword()))
            errors.rejectValue("oldPassword", "account.oldPassword.blank",
                    "account.oldPassword.blank");
        else
            if(isInvalidOldPassword(accountDTO))
                errors.rejectValue("oldPassword", "account.oldPassword.invalid",
                        "account.oldPassword.invalid");
    }

    private boolean isBlankPassword(String password) {
        return password == null || password.trim().isEmpty();
    }

    private boolean isInvalidOldPassword(AccountDTO accountDTO) {
        Account account = accountService.findByAccountId(accountDTO.getId());
//        String encryptPassword = passwordEncoder.encode(accountDTO.getPassword());
//        if (account.getPassword() == encryptPassword)
//            return true;
        return false;
    }

    private void checkNewPassword(AccountDTO accountDTO, Errors errors) {
        String newPassword = accountDTO.getOldPassword();
        if (isBlankPassword(newPassword))
            errors.rejectValue("newPassword", "account.newPassword.blank",
                    "account.newPassword.blank");
        else
            if (isSizeValidNewPassword(newPassword))
                errors.rejectValue("newPassword", "account.newPassword.size",
                        "account.newPassword.size");
    }

    private boolean isSizeValidNewPassword(String newPassword) {
        return newPassword.length() < SIZE_PASSWORD;
    }

    private void checkConfirmPassword(AccountDTO accountDTO, Errors errors) {
        String newPassword = accountDTO.getOldPassword();
        if (isBlankPassword(newPassword))
            errors.rejectValue("confirmNewPassword", "account.confirmNewPassword.blank",
                    "account.confirmNewPassword.blank");
        else
        if (isEqualWithNewPassword(accountDTO))
            errors.rejectValue("confirmNewPassword", "account.confirmNewPassword.size",
                    "account.confirmNewPassword.size");
    }

    private boolean isEqualWithNewPassword(AccountDTO accountDTO) {
        return accountDTO.getNewPassword().equalsIgnoreCase(accountDTO.getConfirmNewPassword());
    }

}
