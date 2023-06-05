package com.project.tutoronline.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.EmailAccountDTO;
import com.project.tutoronline.model.dto.MailDTO;
import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.service.AccountService;
import com.project.tutoronline.service.EmailService;
import com.project.tutoronline.service.impl.EmailServiceImpl;
import com.project.tutoronline.utils.DateUtil;
import com.project.tutoronline.utils.ValidatorUtil;
import com.project.tutoronline.validator.ForgotPasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Controller
@RequestMapping(value = "/forgot-password")
public class ForgotPasswordController {

    private static final String REDIRECT_URL = "/forgot-password";

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ForgotPasswordValidator forgotPasswordValidator;

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
    public String register(Model model, @Valid AccountDTO accountDTO) {
        try {
            String message = "";
            String email = accountDTO.getEmail();
            if (email == null || email.trim().equalsIgnoreCase("") || !email.contains("@")) {
                message = "Vui lòng nhập địa chỉ Email của bạn!";
            } else {
                // save
                Account account = accountService.findByEmail(email);
                if (account == null) {
                    message = "Tài khoản không tồn tại trong hệ thống!";
                } else {
                    // send mail
                    EmailAccountDTO emailDTO = new EmailAccountDTO();
                    emailDTO.setId(account.getId());
                    emailDTO.setFullName(account.getFullName());
                    emailDTO.setEmail(account.getEmail());

                    emailService.sendEmailForForgotPassword(emailDTO);
                    message = "Yêu cầu cấp lại mật khẩu mới thành công!";
                }
            }
            model.addAttribute("error", message);
            model.addAttribute("accountDTO", accountDTO);
            return "forgot_password";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/confirm")
    public String confirmForgotPassword(Model model, @RequestParam String ref) {
        try {
            AccountDTO accountDTO = new AccountDTO();
            if (ref == null || ref.equalsIgnoreCase("")) {
                model.addAttribute("error", "Tài khoản không tồn tại trong hệ thống!");
                model.addAttribute("accountDTO", accountDTO);
            } else {
                String result = EmailServiceImpl.decoderBase64ToString(ref);
                ObjectMapper objectMapper = new ObjectMapper();
                MailDTO mailDTO = objectMapper.readValue(result, MailDTO.class);
                Date currentTime = new Date();
                Date time = DateUtil.convertStringToDate(mailDTO.getTime(), "HH:mm:ss dd-MM-yyyy");

                // adding 30 mins to the time
                Date afterAdding30Minutes = new Date(time.getTime() + (30 * 60 * 1000));
                if (currentTime.after(afterAdding30Minutes)) {
                    model.addAttribute("errorExpire", "Đã hết thời gian xác thực tài khoản, " +
                            "vui lòng gửi lại yêu cầu!");
                    model.addAttribute("accountDTO", accountDTO);
                    return "forgot_password";
                } else {
                    LinkedHashMap<String, String> linkedHashMap = ((LinkedHashMap) mailDTO.getData());
                    long id = Long.parseLong(String.valueOf(linkedHashMap.get("id")));
                    accountDTO.setId(id);
                    model.addAttribute("accountDTO", accountDTO);
                    model.addAttribute("errorList", new HashMap<>());
                }
            }
            return "forgot_password_form";
        } catch (Exception ex) {
            String redirectUrl = "/forgot-password";
            return "redirect:" + redirectUrl;
        }
    }

    @PostMapping("/confirm/")
    public String confirmForgotPassword(Model model, AccountDTO accountDTO, BindingResult bindingResult) {
        String redirectUrl = "/forgot-password/confirm";
        try {
            Account account = accountService.findById(accountDTO.getId());
            //validate
            forgotPasswordValidator.validate(accountDTO, bindingResult);
            if (bindingResult.hasErrors()) {
                model.addAttribute("errorList", ValidatorUtil.toErrors(bindingResult.getFieldErrors()));
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "forgot_password_form";
            } else {
                String encodedPassword = passwordEncoder.encode(accountDTO.getNewPassword());
                account.setPassword(encodedPassword);
                accountService.save(account);
                model.addAttribute("changePassword", "changePassword");
                return "login";
            }
        } catch (Exception ex) {
            return "redirect:" + redirectUrl;
        }
    }

}
