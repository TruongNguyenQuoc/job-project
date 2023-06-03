package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.TutorDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.model.entity.TutorTeachingClass;
import com.project.tutoronline.model.entity.TutorTeachingClassId;
import com.project.tutoronline.repository.TutorRepository;
import com.project.tutoronline.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorServiceImpl implements TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Tutor> findAll() {
        return tutorRepository.findAll();
    }

    @Override
    public Tutor findById(long id) {
        return tutorRepository.findById(id).orElse(null);
    }

    @Override
    public Tutor save(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    @Override
    public Tutor findByAccount(Account account) {
        return tutorRepository.findByAccount(account);
    }

    @Override
    public Tutor registerTutor(Tutor tutor, TutorDTO tutorDTO) {
        Account account = new Account();
        AccountDTO accountDTO = tutorDTO.getAccountDTO();
        account.setFullName(accountDTO.getFullName());
        account.setUsername(accountDTO.getEmail());
        account.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        account.setEmail(accountDTO.getEmail());
        account.setPhone(tutorDTO.getPhone());
        account.setStatus(true);
        account.setRole(roleService.findByName("TUTOR"));
        tutor.setAccount(account);
        tutor.setStatus(false);

        accountService.save(account);
        return save(tutor);
    }
}
