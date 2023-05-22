package com.project.tutoronline.model.mapper;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.entity.Account;

import java.util.List;

public interface AccountMapper {

    AccountDTO toDTO(Account account);

    List<AccountDTO> toListDTO(List<Account> accounts);

    Account toEntity(Account account, AccountDTO accountDTO);

}
