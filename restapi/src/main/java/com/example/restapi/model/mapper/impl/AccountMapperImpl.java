package com.example.restapi.model.mapper.impl;

import com.example.restapi.model.mapper.AccountMapper;
import com.example.restapi.model.dto.AccountDTO;
import com.example.restapi.model.dto.RoleDTO;
import com.example.restapi.model.entity.Account;
import com.example.restapi.model.entity.Role;
import com.example.restapi.model.mapper.AccountMapper;
import com.example.restapi.service.RoleService;
import com.example.restapi.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapperImpl implements AccountMapper {

    @Autowired
    private RoleService roleService;

    @Override
    public AccountDTO accountToAccountDTO(Account account) {
        if (account == null) {
            return new AccountDTO();
        }

        return setAttributesAccountDTO(account);
    }
    @Override
    public List<AccountDTO> listAccountToListAccountDTO(List<Account> accounts) {
        if (accounts == null) {
            return new ArrayList<>();
        }

        List<AccountDTO> result = new ArrayList<>(accounts.size());
        for (Account account : accounts) {
            AccountDTO accountDTO = accountToAccountDTO(account);
            if (accountDTO != null) {
                result.add(accountDTO);
            }
        }
        return result;
    }

    @Override
    public Account accountDTOToAccount(Account account, AccountDTO accountDTO) {
        if (accountDTO == null) {
            return new Account();
        }

        return setAttributesAccount(account, accountDTO);
    }

    private AccountDTO setAttributesAccountDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO();

        if (account.getId() != null) {
            accountDTO.setId(account.getId());
        }
        accountDTO.setUsername(account.getUsername());
        accountDTO.setPassword("***********");
        accountDTO.setFullName(account.getFullName());
        accountDTO.setEmail(account.getEmail());
        accountDTO.setPhone(account.getPhone());
        accountDTO.setAddress(account.getAddress());
        accountDTO.setGender(account.getGender());
        accountDTO.setBirthDay(DateUtil.convertDateToString(account.getBirthday(), "dd/MM/yyyy"));
        accountDTO.setAvatar(account.getAvatar());
        accountDTO.setSchool(account.getSchool());
        accountDTO.setStatus(account.isStatus());

        if (account.getRole() != null) {
            RoleDTO roleDTO = setAttributesRoleDTO(account);
            accountDTO.setRoleId(roleDTO.getId());
            accountDTO.setRoleDTO(roleDTO);
        }
        return accountDTO;
    }

    private RoleDTO setAttributesRoleDTO(Account account){
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(account.getRole().getId());
        roleDTO.setName(account.getRole().getName());
        roleDTO.setDescription(account.getRole().getDescription());
        return roleDTO;
    }

    private Account setAttributesAccount(Account account, AccountDTO accountDTO){
        account.setFullName(accountDTO.getFullName());
        account.setUsername(accountDTO.getUsername());
        account.setAddress(accountDTO.getAddress());
        account.setGender(accountDTO.getGender());
        account.setSchool(accountDTO.getSchool());
        account.setBirthday(DateUtil.convertStringToDate(accountDTO.getBirthDay(), "dd/MM/yyyy"));
        account.setEmail(accountDTO.getEmail());
        account.setPhone(accountDTO.getPhone());
        account.setAvatar(accountDTO.getAvatar());
        account.setStatus(accountDTO.isStatus());

        if (accountDTO.getRoleId() > 0) {
            Role role = roleService.findById(accountDTO.getRoleId());
            account.setRole(role);
        }

        return account;
    }

}
