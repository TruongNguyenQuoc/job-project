package com.example.restapi.validator;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ForgotPasswordValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountDTO accountDTO = (AccountDTO) target;
        checkEmail(accountDTO, errors);
    }

    private void checkEmail(AccountDTO accountDTO, Errors errors) {
        if (accountDTO.getEmail().isEmpty())
            errors.rejectValue("email", "account.email.blank",
                    "account.email.blank");
        else
        if(isExistsEmail(accountDTO))
            errors.rejectValue("email", "account.email.notExists",
                    "account.email.notExists");
    }

    private boolean isExistsEmail(AccountDTO accountDTO) {
        Account account = accountService.findByEmail(accountDTO.getEmail());
        return account == null;
    }
}
