package com.project.tutoronline.controller.front;

import com.project.tutoronline.model.dto.TutorDTO;
import com.project.tutoronline.model.entity.*;
import com.project.tutoronline.model.mapper.ParentMapper;
import com.project.tutoronline.model.mapper.TeachingClassMapper;
import com.project.tutoronline.model.mapper.TutorMapper;
import com.project.tutoronline.service.*;
import com.project.tutoronline.validator.TutorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/change-profile")
public class ChangeProfileController {

    private static final String REDIRECT_URL_TUTOR = "/change-profile/tutor/";

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorMapper tutorMapper;

    @Autowired
    private ParentService parentService;

    @Autowired
    private ParentMapper parentMapper;

    @Autowired
    private TutorValidator tutorValidator;

    @Autowired
    private TeachingClassMapper teachingClassMapper;

    @Autowired
    private TeachingClassService teachingClassService;

    @Autowired
    private TutorTeachingClassService tutorTeachingClassService;

    @GetMapping(value = {"/tutor", "/tutor/"})
    public String changeProfileTutor(Model model, Authentication authentication) {
        CustomAccountDetails customAccountDetails = (CustomAccountDetails) authentication.getPrincipal();
        Account account = customAccountDetails.getAccount();
        Tutor tutor = tutorService.findByAccount(account);

        model.addAttribute("tutorDTO", tutorMapper.toDTO(tutor));
        model.addAttribute("teachingClassDTOList", teachingClassMapper.toListDTO(teachingClassService.findAll()));
        return "/front/change_profile_tutor";
    }

    @PostMapping(value = {"/tutor", "/tutor/"})
    public String changeProfileTutor(Model model, @Valid TutorDTO tutorDTO, BindingResult bindingResult) {
        try {
            String redirectUrlSuccess;
            // validate
            tutorValidator.validate(tutorDTO, bindingResult);
            if (!bindingResult.hasErrors()) {
                // save
                Tutor tutor = tutorService.findById(tutorDTO.getId());

                tutorService.save(tutor, tutorDTO);
                List<TutorTeachingClass> list = tutorTeachingClassService.findByTutor(tutor);
                list.forEach(element -> tutorTeachingClassService.delete(element));

                List<String> tutorTeachingClasses = tutorDTO.getTeachingClassIdList();
                tutorTeachingClasses.forEach(
                        element -> {
                            TutorTeachingClass tutorTeachingClass = new TutorTeachingClass();

                            TutorTeachingClassId tutorTeachingClassId = new TutorTeachingClassId();
                            tutorTeachingClassId.setTutorId(tutor.getId());
                            tutorTeachingClassId.setTeachingClassId(Long.parseLong(element));

                            tutorTeachingClass.setKeyId(tutorTeachingClassId);
                            tutorTeachingClass.setTutor(tutor);
                            tutorTeachingClass.setTeachingClass(teachingClassService.findById(Long.parseLong(element)));
                            tutorTeachingClass.setStatus(true);
                            tutorTeachingClassService.save(tutorTeachingClass);
                        }
                );

                redirectUrlSuccess = "/front/profile/tutor";
                return "redirect:" + redirectUrlSuccess;
            }
            List<TeachingClass> teachingClassList = teachingClassService.findAll();
            model.addAttribute("tutorDTO", tutorDTO);
            model.addAttribute("teachingClassDTOList", teachingClassMapper.toListDTO(teachingClassList));
            return "/front/change_profile_tutor";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL_TUTOR;
        }
    }

    @GetMapping(value = {"/parent", "/parent/"})
    public String changeProfileParent(Model model, Authentication authentication) {
        CustomAccountDetails customAccountDetails = (CustomAccountDetails) authentication.getPrincipal();
        Account account = customAccountDetails.getAccount();
        Parent parent = parentService.findByAccount(account);

        model.addAttribute("parentDTO", parentMapper.toDTO(parent));
        return "/front/change_profile_parent";
    }

}
