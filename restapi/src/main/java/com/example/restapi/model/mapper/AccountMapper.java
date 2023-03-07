package com.example.restapi.model.mapper;

import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.entity.Account;

import java.util.List;

public interface AccountMapper {

    AccountDTO accountToAccountDTO(Account account);

    List<AccountDTO> listAccountToListAccountDTO(List<Account> accounts);

    Account accountDTOToAccount(Account account, AccountDTO accountDTO);

}
