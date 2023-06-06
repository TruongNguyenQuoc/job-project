package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.EmailTemplateDTO;
import com.project.tutoronline.model.dto.FileDTO;
import com.project.tutoronline.model.dto.TutorDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.repository.TutorRepository;
import com.project.tutoronline.service.*;
import com.project.tutoronline.utils.DateUtil;
import com.project.tutoronline.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public List<Tutor> findAll() {
        return tutorRepository.findAll();
    }

    @Override
    public List<Tutor> findByRandom(int limit) {
        return tutorRepository.findByRandom(limit);
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
    public Tutor save(Tutor tutor, TutorDTO tutorDTO) {
        if (tutorDTO.getIdPhotoMul() != null && !ObjectUtils.isEmpty(tutorDTO.getIdPhotoMul().getOriginalFilename())) {
            FileDTO fileDTOBack = fileUploadService.uploadFile(tutorDTO.getIdPhotoMul());
            tutor.setIdPhoto(fileDTOBack.getPath());
        }

        if (tutorDTO.getCardPhotoMul() != null && !ObjectUtils.isEmpty(tutorDTO.getCardPhotoMul().getOriginalFilename())) {
            FileDTO fileDTOBack = fileUploadService.uploadFile(tutorDTO.getCardPhotoMul());
            tutor.setCardPhoto(fileDTOBack.getPath());
        }

        if (tutorDTO.getDegreePhotoMul() != null && !ObjectUtils.isEmpty(tutorDTO.getDegreePhotoMul().getOriginalFilename())) {
            FileDTO fileDTOBack = fileUploadService.uploadFile(tutorDTO.getDegreePhotoMul());
            tutor.setDegreePhoto(fileDTOBack.getPath());
        }

        tutor.setAddress(tutorDTO.getAddress());
        tutor.setBirthday(DateUtil.convertStringToDate(tutorDTO.getBirthday(), "dd-MM-yyyy"));
        tutor.setOrigin(tutorDTO.getOrigin());
        tutor.setIdNumber(tutorDTO.getIdNumber());
        tutor.setAdvantage(tutorDTO.getAdvantage());
        tutor.setIdPhoto(tutorDTO.getIdPhoto());
        tutor.setCardPhoto(tutorDTO.getCardPhoto());
        tutor.setDegreePhoto(tutorDTO.getDegreePhoto());

        tutor.setSchool(tutorDTO.getSchool());
        tutor.setSpecialization(tutorDTO.getSpecialization());

        if (ValidatorUtil.isEmpty(tutorDTO.getYearCollege())) {
            tutor.setYearCollege(tutorDTO.getYearCollege1() + "-" +tutorDTO.getYearCollege2());
        } else {
            tutor.setYearCollege(tutorDTO.getYearCollege());
        }

        tutor.setLevel(tutorDTO.getLevel());

        return tutor;
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
