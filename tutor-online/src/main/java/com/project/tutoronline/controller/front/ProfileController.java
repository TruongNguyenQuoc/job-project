package com.project.tutoronline.controller.front;

import com.project.tutoronline.model.entity.*;
import com.project.tutoronline.model.mapper.JobMapper;
import com.project.tutoronline.model.mapper.ParentMapper;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.model.mapper.TutorMapper;
import com.project.tutoronline.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/front/profile")
public class ProfileController {

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorMapper tutorMapper;

    @Autowired
    private ParentService parentService;

    @Autowired
    private ParentMapper parentMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobMapper jobMapper;

    @GetMapping(value = {"/tutor", "/tutor/"})
    public String profileTutor(Model model, Authentication authentication) {
        CustomAccountDetails customUserDetails = (CustomAccountDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();
        Tutor tutor = tutorService.findByAccount(account);
        List<Job> jobList = jobService.findByTutor(tutor);

        model.addAttribute("tutorDTO", tutorMapper.toDTO(tutor));
        model.addAttribute("jobDTOList", jobMapper.toListDTO(jobList));
        return "/front/profile_tutor";
    }

    @GetMapping(value = {"/parent", "/parent/"})
    public String profileParent(Model model, Authentication authentication) {
        CustomAccountDetails customUserDetails = (CustomAccountDetails) authentication.getPrincipal();
        Account account = customUserDetails.getAccount();
        Parent parent = parentService.findByAccount(account);
        List<Post> postList = postService.findByAccount(account);

        model.addAttribute("parentDTO", parentMapper.toDTO(parent));
        model.addAttribute("postDTOList", postMapper.toListDTO(postList));
        return "/front/profile_parent";
    }

}
