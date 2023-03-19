package com.example.restapi.service.impl;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.mapper.AccountMapper;
import com.example.restapi.repository.AccountRepository;
import com.example.restapi.service.AccountService;
import com.example.restapi.validator.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findByAccountId(long accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new ExceptionHandler("Account not found"));
    }

    @Override
    public List<Account> findByFullName(String fullName) {
        return accountRepository.findAccountByFullName(fullName);
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElseThrow(() -> new ExceptionHandler("Account not found"));
    }

    @Override
    public Account findByPhone(String phone) {
        return accountRepository.findAccountByPhone(phone).orElseThrow(() -> new ExceptionHandler("Account not found"));
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findAccountByEmail(email).orElseThrow(() -> new ExceptionHandler("Account not found"));
    }

    @Override
    public Account saveAccount(AccountDTO accountDTO) {
        return accountRepository.save(setPasswordAndStatus(accountDTO));
    }

    private Account setPasswordAndStatus(AccountDTO accountDTO) {
        Account account = new Account();
        String encryptPassword = passwordEncoder.encode(accountDTO.getPassword());
        account.setPassword(encryptPassword);
        account.setStatus(true);
        return accountMapper.accountDTOToAccount(account, accountDTO);
    }

    @Override
    public Account updateAccount(Account account, AccountDTO accountDTO) {
        Account result = accountMapper.accountDTOToAccount(account, accountDTO);
        return accountRepository.save(result);
    }
}
