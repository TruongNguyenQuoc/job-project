package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.entity.Parent;
import com.project.tutoronline.repository.ParentRepository;
import com.project.tutoronline.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Override
    public List<Parent> findAll() {
        return parentRepository.findAll();
    }

    @Override
    public Parent findById(long id) {
        return parentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Parent> findByFullName(String fullName) {
        return parentRepository.findByFullName(fullName);
    }

    @Override
    public Parent save(Parent parent) {
        return parentRepository.save(parent);
    }
}
