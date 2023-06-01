package com.project.tutoronline.controller.back;

import com.project.tutoronline.service.AccountService;
import com.project.tutoronline.service.ParentService;
import com.project.tutoronline.service.PostService;
import com.project.tutoronline.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/back/dashboard")
public class DashboardController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ParentService parentService;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private PostService postService;

    @GetMapping(value = {"", "/"})
    public String dashboard(Model model) {

        model.addAttribute("totalAccount", accountService.findAll().size());
        model.addAttribute("totalParent", parentService.findAll().size());
        model.addAttribute("totalTutor", tutorService.findAll().size());
        model.addAttribute("totalPost", postService.findAll().size());

        return "back/dashboard";
    }
}
