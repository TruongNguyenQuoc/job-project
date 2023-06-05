package com.project.tutoronline.controller.front;

import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Job;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.model.mapper.TutorMapper;
import com.project.tutoronline.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/checkout-job")
public class CheckoutJobController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorMapper tutorMapper;

    @Autowired
    private JobService jobService;

    @GetMapping("/{id}")
    public String checkout(Model model, @RequestParam(required = false) String action,
                           @PathVariable long id, Authentication authentication) {
        CustomAccountDetails customAccountDetails = (CustomAccountDetails) authentication.getPrincipal();
        Account account = customAccountDetails.getAccount();
        Tutor tutor = tutorService.findByAccount(account);
        Post post = postService.findById(id);

        if (action == null) {
            model.addAttribute("tutorDTO", tutorMapper.toDTO(tutor));
            model.addAttribute("postDTO", postMapper.toDTO(post));
            return "/front/checkout_job";
        }
        Job job = new Job();
        job.setPost(post);
        job.setTutor(tutor);
        post.setProgress("Đang Xác Nhận Gia Sư");
        postService.save(post);
        jobService.save(job);
        String redirectUrl = "/front/profile/tutor/";
        return "redirect:" + redirectUrl;
    }

}
