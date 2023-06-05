package com.project.tutoronline.service.impl;

import com.project.tutoronline.model.dto.EmailTemplateDTO;
import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Job;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.repository.JobRepository;
import com.project.tutoronline.service.EmailService;
import com.project.tutoronline.service.JobService;
import com.project.tutoronline.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private EmailService emailService;

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job findById(long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public Job findByPost(Post post) {
        return jobRepository.findByPost(post);
    }

    @Override
    public List<Job> findByTutor(Tutor tutor) {
        return jobRepository.findByTutor(tutor);
    }

    @Override
    public Job save(Job job) {
        List<String> toEmails = new ArrayList<>();

        PostDTO postDTO = postMapper.toDTO(job.getPost());
        Account accountTutor = job.getTutor().getAccount();

        toEmails.add(accountTutor.getEmail());

        String[] arrayToEmail = toEmails.toArray(String[]::new);
        String tutorInformation = accountTutor.getPhone() + "-" + accountTutor.getEmail();
        String postInformation = job.getPost().getCourse().getName() + " - " + job.getPost().getTeachingClass().getName()
                                                            + " - " + job.getPost().getAddress();
        String postCode = postDTO.getCode();
        String timeTeaching = postDTO.getTimeTeachingName();
        String postPrice = postDTO.getPrice();
        String postRequirement = postDTO.getRequirement();

        // send email
        Map<String, Object> properties = new HashMap<>();
        properties.put("tutor", accountTutor.getFullName());
        properties.put("tutorInformation", tutorInformation);
        properties.put("postCode", postCode);
        properties.put("timeTeaching", timeTeaching);
        properties.put("postInformation", postInformation);
        properties.put("postPrice", postPrice);
        properties.put("postRequirement", postRequirement);
        properties.put("emailContact", "Email: tutoronline@gmail.com");
        properties.put("phoneContact", "Hotline: 0123-456-789");

        EmailTemplateDTO emailTemplateDTO = new EmailTemplateDTO();
        emailTemplateDTO.setTo(arrayToEmail);
        emailTemplateDTO.setSubject("Việc Làm " + job.getPost().getCourse().getName().toUpperCase() + " tại www.tutoronline.vn");
        emailTemplateDTO.setContent("");
        emailTemplateDTO.setProperties(properties);

        emailService.sendEmailForCheckout(emailTemplateDTO);

        return jobRepository.save(job);
    }
}
