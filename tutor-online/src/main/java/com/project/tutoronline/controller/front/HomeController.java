package com.project.tutoronline.controller.front;

import com.project.tutoronline.model.entity.Job;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.model.mapper.TutorMapper;
import com.project.tutoronline.service.JobService;
import com.project.tutoronline.service.PostService;
import com.project.tutoronline.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private JobService jobService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorMapper tutorMapper;

    @GetMapping(value = {"", "/", "/trang-chu.html"})
    public String home(Model model) {
        List<Job> jobList = jobService.findAll();
        List<Post> postList = postService.findByRandom(6);
        List<Tutor> tutorList = tutorService.findByRandom(4);

        model.addAttribute("jobSize", jobList.size());
        model.addAttribute("postList", postMapper.toListDTO(postList));
        model.addAttribute("tutorList", tutorMapper.toListDTO(tutorList));
        return "front/home";
    }

}
