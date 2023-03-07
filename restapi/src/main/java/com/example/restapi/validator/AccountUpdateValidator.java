package com.example.restapi.validator;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.service.AccountService;
import com.example.restapi.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AccountUpdateValidator implements Validator {

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
            checkAccountByEmail(accountDTO, errors);
            checkAccountByPhone(accountDTO, errors);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkAccountByPhone(AccountDTO accountDTO, Errors errors) {
        String phone = accountDTO.getPhone();

        if (ValidatorUtil.isEmpty(phone))
            errors.rejectValue("phone", "account.phone.blank",
                    "account.phone.blank");
        else
            checkFormatPhone(accountDTO, errors);
    }

    private void checkFormatPhone(AccountDTO accountDTO, Errors errors) {
        if (!ValidatorUtil.checkFormatPhoneNumber(accountDTO.getPhone()))
            errors.rejectValue("phone", "account.phone.format",
                    "account.phone.format");
        else
            checkExistPhone(accountDTO, errors);
    }

    private void checkExistPhone(AccountDTO accountDTO, Errors errors) {
        Account account = accountService.findByPhone(accountDTO.getPhone());
        if (account.getId() != accountDTO.getId())
            errors.rejectValue("phone", "account.phone.exists",
                    "account.phone.exists");
    }

    private void checkAccountByEmail(AccountDTO accountDTO, Errors errors) {
        String phone = accountDTO.getEmail();
        if (ValidatorUtil.isEmpty(phone))
            errors.rejectValue("email", "account.email.blank",
                    "account.email.blank");
        else
            checkFormatEmail(accountDTO, errors);
    }

    private void checkFormatEmail(AccountDTO accountDTO, Errors errors) {
        if (!ValidatorUtil.checkFormatEmail(accountDTO.getEmail()))
            errors.rejectValue("email", "account.email.format",
                    "account.email.format");
    }
}
