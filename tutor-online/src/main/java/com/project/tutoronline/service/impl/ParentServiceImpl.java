package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.ParentDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Parent;
import com.project.tutoronline.repository.ParentRepository;
import com.project.tutoronline.service.ParentService;
import com.project.tutoronline.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Parent> findAll() {
        return parentRepository.findAll();
    }

    @Override
    public Parent findById(long id) {
        return parentRepository.findById(id).orElse(null);
    }

    @Override
    public Parent findByAccount(Account account) {
        return parentRepository.findByAccount(account);
    }

    @Override
    public Parent save(Parent parent) {
        return parentRepository.save(parent);
    }

    @Override
    public Parent registerParent(Parent parent, ParentDTO parentDTO) {
        Account account = new Account();
        AccountDTO accountDTO = parentDTO.getAccountDTO();
        account.setFullName(accountDTO.getFullName());
        account.setUsername(accountDTO.getEmail());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        account.setEmail(accountDTO.getEmail());
        account.setPhone(parentDTO.getPhone());
        account.setStatus(true);
        account.setRole(roleService.findByName("PARENT"));
        parent.setAccount(account);
        parent.setStatus(true);

        return save(parent);
    }
}
