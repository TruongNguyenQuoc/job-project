package com.example.restapi.service.impl;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.mapper.AccountMapper;
import com.example.restapi.repository.AccountRepository;
import com.example.restapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findByAccountId(long accountId) {
        return accountRepository.findById(accountId).orElse(new Account());
    }

    @Override
    public List<Account> findByFullName(String fullName) {
        return accountRepository.findAccountByFullName(fullName);
    }

    @Override
    public Account findByPhone(String phone) {
        return accountRepository.findAccountByPhone(phone).orElse(new Account());
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findAccountByEmail(email).orElse(new Account());
    }

    @Override
    public Account saveAccount(AccountDTO accountDTO) {
        Account account = new Account();
//        String encryptPassword = passwordEncoder.encode(accountDTO.getPassword());
//        account.setPassword(encryptPassword);
        account.setStatus(true);
        account = accountMapper.accountDTOToAccount(account, accountDTO);
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Account account, AccountDTO accountDTO) {
        account = accountMapper.accountDTOToAccount(account, accountDTO);
        return accountRepository.save(account);
    }
}
