package com.project.tutoronline.model.mapper.impl;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.RoleDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Role;
import com.project.tutoronline.model.mapper.AccountMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public AccountDTO toDTO(Account account) {
        if (account == null) {
            return null;
        }

        // account
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setUsername(account.getUsername());
        accountDTO.setStatus(account.isStatus());

        // role
        RoleDTO roleDTO = new RoleDTO();
        Role role = account.getRole();
        if (role != null) {
            roleDTO.setId(role.getId());
            roleDTO.setDescription(role.getDescription());
            roleDTO.setName(role.getName());
            roleDTO.setStatus(role.isStatus());
        }
        accountDTO.setRole(roleDTO.getName());
        accountDTO.setRoleDTO(roleDTO);

        return accountDTO;
    }

    @Override
    public List<AccountDTO> toListDTO(List<Account> accounts) {
        if (accounts == null)
            return null;

        List<AccountDTO> result = new ArrayList<>();
        accounts.forEach(element -> result.add(toDTO(element)));

        return result;
    }

    @Override
    public Account toEntity(Account account, AccountDTO accountDTO) {
        if (account == null) {
            return null;
        }

        // set account
        account.setUsername(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        account.setStatus(accountDTO.isStatus());

        return account;
    }

}
