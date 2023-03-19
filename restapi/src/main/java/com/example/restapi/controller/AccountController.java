package com.example.restapi.controller;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.dto.RestResponseDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.mapper.AccountMapper;
import com.example.restapi.service.AccountService;
import com.example.restapi.utils.ValidatorUtil;
import com.example.restapi.validator.AccountUpdateValidator;
import com.example.restapi.validator.AccountValidator;
import com.example.restapi.validator.ForgotPasswordValidator;
import com.example.restapi.validator.PasswordValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private AccountUpdateValidator accountUpdateValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private ForgotPasswordValidator forgotPasswordValidator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<RestResponseDTO<List<AccountDTO>>> getAllAccount() {
        RestResponseDTO<List<AccountDTO>> restResponse = new RestResponseDTO<>();
        List<Account> accounts = accountService.findAll();
        restResponse.ok(accountMapper.listAccountToListAccountDTO(accounts));
        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }

    @GetMapping("/id/{accountId}")
    public ResponseEntity<RestResponseDTO<AccountDTO>> getAccountByAccountId(@PathVariable long accountId) {
        RestResponseDTO<AccountDTO> restResponse = new RestResponseDTO<>();
        Account account = accountService.findByAccountId(accountId);
        restResponse.ok(accountMapper.accountToAccountDTO(account));
        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<RestResponseDTO<AccountDTO>> saveAccount(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        RestResponseDTO restResponse = new RestResponseDTO();

        accountValidator.validate(accountDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            restResponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
        }

        Account account = accountService.saveAccount(accountDTO);
        restResponse.ok(accountMapper.accountToAccountDTO(account));
        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<RestResponseDTO> updateAccount(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        RestResponseDTO restResponse = new RestResponseDTO();

        accountUpdateValidator.validate(accountDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            restResponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
        }

        Account account = accountService.findByAccountId(accountDTO.getId());
        if (account.getId() != null) {
            accountService.updateAccount(account, accountDTO);
            restResponse.ok(accountMapper.accountToAccountDTO(account));
            return new ResponseEntity<>(restResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<RestResponseDTO> changePassword(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        RestResponseDTO restResponse = new RestResponseDTO();

        passwordValidator.validate(accountDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            restResponse.fail(validatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
        }

        Account account = accountService.findByAccountId(accountDTO.getId());
        if (account.getId() != null) {
            String encodedPassword = passwordEncoder.encode(accountDTO.getNewPassword());
            account.setPassword(encodedPassword);
            accountService.updateAccount(account, accountDTO);
            restResponse.ok(accountMapper.accountToAccountDTO(account));
            return new ResponseEntity<>(restResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/forgot")
    public ResponseEntity<RestResponseDTO> forgotPassword(@Valid @RequestBody AccountDTO accountDTO, BindingResult bindingResult) {
        RestResponseDTO restResponse = new RestResponseDTO();

        forgotPasswordValidator.validate(accountDTO, bindingResult);

        String email = accountDTO.getEmail();
        Account account = accountService.findByEmail(email);
        if (account == null) {
            bindingResult.rejectValue("email", "account.email.notExists",
                    "account.email.notExists");
        } else {
//            EmailAccountDTO emailDTO = new EmailAccountDTO();
//            emailDTO.setId(account.getId());
//            emailDTO.setFullname(account.getFullName());
//            emailDTO.setEmail(account.getEmail());
//            emailDTO.setPassword("12345678");
//            emailService.sendEmailForResetPassword(emailDTO, account);
//            bindingResult.rejectValue("email", "account.email.success",
//                    "account.email.success");
        }
        return new ResponseEntity<>(restResponse, HttpStatus.OK);
    }
}
