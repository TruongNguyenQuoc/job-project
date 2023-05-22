package com.project.tutoronline.controller;

import com.project.tutoronline.model.dto.AccountDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = { "/login" })
public class LoginController {

    @GetMapping(value = {""})
    public String loginPage(Model model) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setUsername("");
        accountDTO.setPassword("");

        model.addAttribute("accountDTO", accountDTO);
        model.addAttribute("error", null);

        return "login";
    }

}
