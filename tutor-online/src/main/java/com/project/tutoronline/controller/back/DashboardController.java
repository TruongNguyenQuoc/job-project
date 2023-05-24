package com.project.tutoronline.controller.back;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/back/dashboard")
public class DashboardController {

    @GetMapping(value = {"", "/"})
    public String dashboard(Model model) {
        return "back/dashboard";
    }
}
