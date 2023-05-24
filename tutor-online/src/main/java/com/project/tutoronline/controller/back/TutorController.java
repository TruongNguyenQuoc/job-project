package com.project.tutoronline.controller.back;

import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.dto.TutorDTO;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.model.mapper.TutorMapper;
import com.project.tutoronline.service.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/tutor")
public class TutorController {

    private static final String REDIRECT_URL = "/back/tutor";

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorMapper tutorMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Tutor> tutorList = tutorService.findAll();
            model.addAttribute("tutorList", tutorMapper.toListDTO(tutorList));
            return "back/tutor_list";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form/{id}"})
    public String form(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            TutorDTO tutorDTO = tutorMapper.toDTO(tutorService.findById(id));
            if (tutorDTO == null) {
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

            model.addAttribute("tutorDTO", tutorDTO);

            return "back/tutor_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, TutorDTO tutorDTO, BindingResult bindingResult) {
        try {
            String redirectUrl;
            Tutor tutor = tutorService.save(tutorMapper.toEntity(tutorDTO));
            if (tutor != null) {
                redirectUrl = "/back/tutor/form/" + tutor.getId() + "?action=save&status=success";
            } else {
                redirectUrl = "/back/tutor/form/" + "?action=error";
            }

            return "redirect:" + redirectUrl;
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        Tutor tutor = tutorService.findById(id);
        tutor.setStatus(false);
        tutorService.save(tutor);
        return "redirect:" + REDIRECT_URL;
    }

}
