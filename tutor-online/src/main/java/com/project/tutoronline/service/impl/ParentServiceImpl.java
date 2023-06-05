package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.EmailTemplateDTO;
import com.project.tutoronline.model.dto.ParentDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Parent;
import com.project.tutoronline.repository.ParentRepository;
import com.project.tutoronline.service.AccountService;
import com.project.tutoronline.service.EmailService;
import com.project.tutoronline.service.ParentService;
import com.project.tutoronline.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParentServiceImpl implements ParentService {

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

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

        return save(parent);
    }
}
