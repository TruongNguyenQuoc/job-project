package com.project.tutoronline.controller.back;

import com.project.tutoronline.model.dto.JobDTO;
import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.entity.Job;
import com.project.tutoronline.model.mapper.JobMapper;
import com.project.tutoronline.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/job")
public class JobController {

    private static final String REDIRECT_URL = "/back/job";
    
    @Autowired
    private JobService jobService;

    @Autowired
    private JobMapper jobMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Job> jobList = jobService.findAll();
            model.addAttribute("jobList", jobMapper.toListDTO(jobList));
            return "back/job_list";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form/{id}"})
    public String form(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            JobDTO jobDTO = jobMapper.toDTO(jobService.findById(id));
            if (jobDTO == null) {
                return "redirect:" + REDIRECT_URL;
            }

            if (action != null) {
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO(action,
                        status.equalsIgnoreCase("success") ?
                                "Cập nhật dữ liệu thành công!" :
                                "Vui lòng kiểm tra lại thông tin!"));
            }

            if (status != null && status.equalsIgnoreCase("success")) {
                model.addAttribute("status", "success");
            }

            model.addAttribute("jobDTO", jobDTO);

            return "back/job_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/form")
    public String save(JobDTO jobDTO) {
        try {
            String redirectUrl = "";
            Job job = jobService.save(jobMapper.toEntity(jobDTO));
            if (job != null) {
                redirectUrl = "/back/job/form/" + job.getId() + "?action=save&status=success";
            } else {
                redirectUrl = "/back/job/form/" + "?action=error";
            }

            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }
    

}
