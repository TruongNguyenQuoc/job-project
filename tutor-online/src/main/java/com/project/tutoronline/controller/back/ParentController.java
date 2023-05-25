package com.project.tutoronline.controller.back;

import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.dto.ParentDTO;
import com.project.tutoronline.model.entity.Parent;
import com.project.tutoronline.model.mapper.ParentMapper;
import com.project.tutoronline.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/parent")
public class ParentController {

    private static final String REDIRECT_URL = "/back/parent";

    @Autowired
    private ParentService parentService;

    @Autowired
    private ParentMapper parentMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Parent> parentList = parentService.findAll();
            model.addAttribute("parentList", parentMapper.toListDTO(parentList));
            return "back/parent_list";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form"})
    public String form(Model model) {
        try {
            model.addAttribute("messageDTO", null);
            model.addAttribute("parentDTO", new ParentDTO());
            return "back/parent_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form/{id}"})
    public String form(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            ParentDTO parentDTO = parentMapper.toDTO(parentService.findById(id));
            if (parentDTO == null) {
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

            model.addAttribute("parentDTO", parentDTO);

            return "back/parent_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, ParentDTO parentDTO, BindingResult bindingResult) {
        try {
            String redirectUrl = "";

            if (bindingResult.hasErrors()) {
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "back/parent_form";
            } else {
                // save
                Parent parent = parentService.save(parentMapper.toEntity(parentDTO));
                if (parent != null) {
                    redirectUrl = "/back/parent/form/" + parent.getId() + "?action=save&status=success";
                } else {
                    redirectUrl = "/back/parent/form/" + "?action=error";
                }

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        Parent parent = parentService.findById(id);
        parent.setStatus(false);
        parentService.save(parent);
        return "redirect:" + REDIRECT_URL;
    }

}
