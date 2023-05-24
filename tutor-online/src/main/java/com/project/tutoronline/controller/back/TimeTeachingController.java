package com.project.tutoronline.controller.back;

import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.dto.TimeTeachingDTO;
import com.project.tutoronline.model.entity.TimeTeaching;
import com.project.tutoronline.model.mapper.TimeTeachingMapper;
import com.project.tutoronline.service.TimeTeachingService;
import com.project.tutoronline.validator.TimeTeachingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/time-teaching")
public class TimeTeachingController {
    
    private static final String REDIRECT_URL = "/back/time-teaching";

    @Autowired
    private TimeTeachingService timeTeachingService;

    @Autowired
    private TimeTeachingValidator timeTeachingValidator;

    @Autowired
    private TimeTeachingMapper timeTeachingMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<TimeTeaching> timeTeachingList = timeTeachingService.findAll();
            model.addAttribute("timeTeachingList", timeTeachingMapper.toListDTO(timeTeachingList));
            return "back/time_teaching_list";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form"})
    public String form(Model model) {
        try {
            model.addAttribute("messageDTO", null);
            model.addAttribute("timeTeachingDTO", new TimeTeachingDTO());
            return "back/time_teaching_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form/{id}"})
    public String form(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            TimeTeachingDTO timeTeachingDTO = timeTeachingMapper.toDTO(timeTeachingService.findById(id));
            if (timeTeachingDTO == null) {
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

            model.addAttribute("timeTeachingDTO", timeTeachingDTO);

            return "back/time_teaching_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, TimeTeachingDTO timeTeachingDTO, BindingResult bindingResult) {
        try {
            String redirectUrl = "";
            // validate
            timeTeachingValidator.validate(timeTeachingDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "back/time_teaching_form";
            } else {
                // save
                TimeTeaching timeTeaching = timeTeachingService.save(timeTeachingMapper.toEntity(timeTeachingDTO));
                if (timeTeaching != null) {
                    redirectUrl = "/back/time-teaching/form/" + timeTeaching.getId() + "?action=save&status=success";
                } else {
                    redirectUrl = "/back/time-teaching/form/" + "?action=error";
                }

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        TimeTeaching timeTeaching = timeTeachingService.findById(id);
        timeTeaching.setStatus(false);
        timeTeachingService.save(timeTeaching);
        return "redirect:" + REDIRECT_URL;
    }
}
