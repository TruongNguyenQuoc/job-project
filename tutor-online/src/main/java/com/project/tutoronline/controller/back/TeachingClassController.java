package com.project.tutoronline.controller.back;

import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.dto.TeachingClassDTO;
import com.project.tutoronline.model.entity.TeachingClass;
import com.project.tutoronline.model.mapper.TeachingClassMapper;
import com.project.tutoronline.service.RoleService;
import com.project.tutoronline.service.TeachingClassService;
import com.project.tutoronline.validator.TeachingClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/teaching-class")
public class TeachingClassController {

    private static final String REDIRECT_URL = "/back/teaching-class";

    @Autowired
    private TeachingClassService teachingClassService;
    @Autowired
    private TeachingClassValidator teachingClassValidator;

    @Autowired
    private TeachingClassMapper teachingClassMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<TeachingClass> teachingClassList = teachingClassService.findAll();
            model.addAttribute("teachingClassList", teachingClassMapper.toListDTO(teachingClassList));
            return "back/teaching_class_list";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form"})
    public String form(Model model) {
        try {
            model.addAttribute("messageDTO", null);
            model.addAttribute("teachingClassDTO", new TeachingClassDTO());
            return "back/teaching_class_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form/{id}"})
    public String form(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            TeachingClassDTO teachingClassDTO = teachingClassMapper.toDTO(teachingClassService.findById(id));
            if (teachingClassDTO == null) {
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

            model.addAttribute("teachingClassDTO", teachingClassDTO);

            return "back/teaching_class_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, TeachingClassDTO teachingClassDTO, BindingResult bindingResult) {
        try {
            String redirectUrl = "";
            // validate
            teachingClassValidator.validate(teachingClassDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "back/teaching_class_form";
            } else {
                // save
                TeachingClass teachingClass = teachingClassService.save(teachingClassMapper.toEntity(teachingClassDTO));
                if (teachingClass != null) {
                    redirectUrl = "/back/teaching-class/form/" + teachingClass.getId() + "?action=save&status=success";
                } else {
                    redirectUrl = "/back/teaching-class/form/" + "?action=error";
                }

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        TeachingClass teachingClass = teachingClassService.findById(id);
        teachingClass.setStatus(false);
        teachingClassService.save(teachingClass);
        return "redirect:" + REDIRECT_URL;
    }

}
