package com.project.tutoronline.controller.front;

import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.model.mapper.TutorMapper;
import com.project.tutoronline.service.CustomAccountDetails;
import com.project.tutoronline.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/front/profile")
public class ProfileController {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorMapper tutorMapper;

    @GetMapping(value = {"/tutor", "/tutor/"})
    public String profile(Model model, Authentication authentication) {

        CustomAccountDetails customUserDetails = (CustomAccountDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();

        Tutor tutor = tutorService.findByAccount(account);

        model.addAttribute("tutorDTO", tutorMapper.toDTO(tutor));
        return "front/profile";
    }

}
