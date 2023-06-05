package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.EmailTemplateDTO;
import com.project.tutoronline.model.dto.TutorDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.repository.TutorRepository;
import com.project.tutoronline.service.AccountService;
import com.project.tutoronline.service.EmailService;
import com.project.tutoronline.service.RoleService;
import com.project.tutoronline.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private EmailService emailService;

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

        account = accountService.save(account);
        boolean isNewAccount = accountDTO.getId() == 0;

        if (isNewAccount && account != null) {
            String email = account.getEmail();
            if (email != null) {
                String[] toEmail = {email};

                Map<String, Object> properties = new HashMap<>();
                properties.put("fullname", account.getFullName());
                properties.put("username", account.getUsername());
                properties.put("password", accountDTO.getPassword());
                properties.put("emailContact", "Email: tutoronline@gmail.com");
                properties.put("phoneContact", "Hotline: 0123-456-789");

                EmailTemplateDTO emailTemplateDTO = new EmailTemplateDTO();
                emailTemplateDTO.setTo(toEmail);
                emailTemplateDTO.setSubject("Xác Nhận Đăng Ký Tài Khoản Tại tutoronline.vn");
                emailTemplateDTO.setContent("");
                emailTemplateDTO.setProperties(properties);

                emailService.sendEmailForRegister(emailTemplateDTO);
            }
        }

        return save(tutor);
    }
}
