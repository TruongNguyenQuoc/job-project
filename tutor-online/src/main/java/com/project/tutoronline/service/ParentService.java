package com.project.tutoronline.service;

import com.project.tutoronline.model.dto.ParentDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Parent;

import java.util.List;

public interface ParentService {

    List<Parent> findAll();

    Parent findById(long id);

    Parent findByAccount(Account account);

    Parent save(Parent parent);

    Parent registerParent(Parent parent, ParentDTO parentDTO);
}
