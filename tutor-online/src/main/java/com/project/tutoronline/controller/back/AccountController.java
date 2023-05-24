package com.project.tutoronline.controller.back;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Role;
import com.project.tutoronline.model.mapper.AccountMapper;
import com.project.tutoronline.service.AccountService;
import com.project.tutoronline.service.RoleService;
import com.project.tutoronline.validator.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/account")
public class AccountController {

    private static final String REDIRECT_URL = "/back/account";

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccountValidator accountValidator;

    @Autowired
    private AccountMapper accountMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Account> accountList = accountService.findAll();
            model.addAttribute("accountList", accountMapper.toListDTO(accountList));
            return "back/account_list";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form"})
    public String form(Model model) {
        try {
            model.addAttribute("messageDTO", null);
            model.addAttribute("accountDTO", new AccountDTO());
            model.addAttribute("roleListDTO", roleService.findAll());
            return "back/account_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form/{id}"})
    public String form(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            AccountDTO accountDTO = accountMapper.toDTO(accountService.findById(id));
            List<Role> roleList = roleService.findAll();
            if (accountDTO == null) {
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

            model.addAttribute("accountDTO", accountDTO);
            model.addAttribute("roleListDTO", roleList);

            return "back/account_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, AccountDTO accountDTO, BindingResult bindingResult) {
        try {
            String redirectUrl = "";
            // validate
            accountValidator.validate(accountDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "back/account_form";
            } else {
                // save
                Account account = accountService.save(accountDTO);
                if (account != null) {
                    redirectUrl = "/back/account/form/" + account.getId() + "?action=save&status=success";
                } else {
                    redirectUrl = "/back/account/form/" + "?action=error";
                }

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        Account account = accountService.findById(id);
        account.setStatus(false);
        accountService.save(account);
        return "redirect:" + REDIRECT_URL;
    }

}
