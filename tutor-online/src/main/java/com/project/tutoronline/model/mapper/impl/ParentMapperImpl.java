package com.project.tutoronline.model.mapper.impl;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.ParentDTO;
import com.project.tutoronline.model.entity.Parent;
import com.project.tutoronline.model.mapper.AccountMapper;
import com.project.tutoronline.model.mapper.ParentMapper;
import com.project.tutoronline.service.AccountService;
import com.project.tutoronline.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParentMapperImpl implements ParentMapper {

    @Autowired
    private ParentService parentService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountService accountService;

    @Override
    public ParentDTO toDTO(Parent parent) {
        if (parent == null) return null;

        ParentDTO parentDTO = new ParentDTO();
        parentDTO.setId(parent.getId());
        parentDTO.setAddress(parent.getAddress());
        parentDTO.setPhone(parent.getAccount().getPhone());
        parentDTO.setAvatar(parent.getAvatar());
        parentDTO.setStatus(parent.isStatus());

        AccountDTO accountDTO = accountMapper.toDTO(parent.getAccount());
        parentDTO.setAccountDTO(accountDTO);
        parentDTO.setAccountId(parent.getAccount().getId());

        return parentDTO;
    }

    @Override
    public List<ParentDTO> toListDTO(List<Parent> parents) {
        if(parents == null) return null;

        List<ParentDTO> result = new ArrayList<>();
        parents.forEach(parent -> result.add(toDTO(parent)));

        return result;
    }

    @Override
    public Parent toEntity(ParentDTO parentDTO) {
        if(parentDTO == null) return null;

        Parent parent = parentService.findById(parentDTO.getId());
        if (parent == null) parent = new Parent();

        parent.setAddress(parentDTO.getAddress());
        parent.setAvatar(parentDTO.getAvatar());
        parent.setStatus(parentDTO.isStatus());

        parent.setAccount(accountService.findById(parentDTO.getAccountId()));

        return parent;
    }
}
