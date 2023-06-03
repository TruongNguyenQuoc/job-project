package com.project.tutoronline.service;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.entity.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();

    Account findById(long id);

    Account findByUsername(String username);

    Account findByEmail(String email);

    Account findByPhone(String phone);

    Account save(AccountDTO accountDTO);

    Account save(Account account);

    Account register(AccountDTO accountDTO);

}
