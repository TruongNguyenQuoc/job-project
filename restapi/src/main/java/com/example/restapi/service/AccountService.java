package com.example.restapi.service;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findByAccountId(long accountId);

    List<Account> findByFullName(String fullName);

    Account findByUsername(String username);

    Account findByPhone(String phone);

    Account findByEmail(String email);

    Account saveAccount(AccountDTO accountDTO);

    Account updateAccount(Account account, AccountDTO accountDTO);
}
