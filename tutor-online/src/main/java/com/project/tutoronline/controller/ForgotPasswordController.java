package com.project.tutoronline.controller;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.service.AccountService;
import com.project.tutoronline.validator.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/forgot-password")
public class ForgotPasswordController {

    private static final String REDIRECT_URL = "/forgot-password";

    @Autowired
    private AccountService accountService;

    @Autowired
    private RegisterValidator registerValidator;

    @GetMapping(value = {"", "/"})
    public String registerPage(Model model) {
        try {
            AccountDTO accountDTO = new AccountDTO();

            model.addAttribute("messageDTO", null);
            model.addAttribute("accountDTO", accountDTO);

            return "forgot_password";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = {"", "/"})
    public String register(Model model, @Valid AccountDTO accountDTO, BindingResult bindingResult) {
        try {
            String redirectUrl = "";
            // validate
            registerValidator.validate(accountDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("messageDTO", new MessageDTO("error",
                        "Vui lòng kiểm tra lại thông tin!"));
                model.addAttribute("accountDTO", accountDTO);
                return "forgot_password";
            } else {
                // save
                Account account = accountService.register(accountDTO);

                if (account != null) {
                    redirectUrl = "/message/register?username=" + account.getUsername();
                    return "redirect:" + redirectUrl;
                }

                model.addAttribute("messageDTO", new MessageDTO("error",
                        "Vui lòng kiểm tra lại thông tin!"));
                model.addAttribute("accountDTO", accountDTO);
                return "forgot_password";
            }
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
