package com.project.tutoronline.controller.front;

import com.project.tutoronline.model.entity.Job;
import com.project.tutoronline.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private JobService jobService;

    @GetMapping(value = {"", "/", "/trang-chu.html"})
    public String home(Model model) {
        List<Job> jobList = jobService.findAll();
        model.addAttribute("jobSize", jobList.size());
        return "front/home";
    }

}
