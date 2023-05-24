package com.project.tutoronline.controller.back;

import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.dto.CourseDTO;
import com.project.tutoronline.model.entity.Course;
import com.project.tutoronline.model.mapper.CourseMapper;
import com.project.tutoronline.service.CourseService;
import com.project.tutoronline.validator.CourseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/course")
public class CourseController {

    private static final String REDIRECT_URL = "/back/course";

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseValidator courseValidator;

    @Autowired
    private CourseMapper courseMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Course> courseList = courseService.findAll();
            model.addAttribute("courseList", courseMapper.toListDTO(courseList));
            return "back/course_list";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form"})
    public String form(Model model) {
        try {
            model.addAttribute("messageDTO", null);
            model.addAttribute("courseDTO", new CourseDTO());
            return "back/course_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form/{id}"})
    public String form(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            CourseDTO courseDTO = courseMapper.toDTO(courseService.findById(id));
            if (courseDTO == null) {
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

            model.addAttribute("courseDTO", courseDTO);
            return "back/course_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, CourseDTO courseDTO, BindingResult bindingResult) {
        try {
            String redirectUrl = "";
            // validate
            courseValidator.validate(courseDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "back/course_form";
            } else {
                // save
                Course course = courseService.save(courseMapper.toEntity(courseDTO));
                if (course != null) {
                    redirectUrl = "/back/course/form/" + course.getId() + "?action=save&status=success";
                } else {
                    redirectUrl = "/back/course/form/" + "?action=error";
                }

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        Course course = courseService.findById(id);
        course.setStatus(false);
        courseService.save(course);
        return "redirect:" + REDIRECT_URL;
    }

}
