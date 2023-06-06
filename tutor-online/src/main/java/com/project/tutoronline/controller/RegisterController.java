package com.project.tutoronline.controller;

import com.project.tutoronline.model.dto.*;
import com.project.tutoronline.model.entity.*;
import com.project.tutoronline.model.mapper.ParentMapper;
import com.project.tutoronline.model.mapper.TeachingClassMapper;
import com.project.tutoronline.model.mapper.TutorMapper;
import com.project.tutoronline.service.*;
import com.project.tutoronline.utils.DateUtil;
import com.project.tutoronline.validator.ParentValidator;
import com.project.tutoronline.validator.RegisterValidator;
import com.project.tutoronline.validator.TutorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    private static final String REDIRECT_URL = "/register";

    @Autowired
    private AccountService accountService;

    @Autowired
    private RegisterValidator registerValidator;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private ParentService parentService;

    @Autowired
    private TutorValidator tutorValidator;

    @Autowired
    private ParentValidator parentValidator;

    @Autowired
    private TeachingClassService teachingClassService;

    @Autowired
    private TutorTeachingClassService tutorTeachingClassService;

    @Autowired
    private TeachingClassMapper teachingClassMapper;

    @Autowired
    private TutorMapper tutorMapper;

    @Autowired
    private ParentMapper parentMapper;

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping(value = {"", "/"})
    public String registerPage(Model model) {
        try {
            AccountDTO accountDTO = new AccountDTO();

            model.addAttribute("messageDTO", null);
            model.addAttribute("accountDTO", accountDTO);

            return "register";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/success", "/success/"})
    public String registerSuccess(Model model, @RequestParam(required = false) String username) {
        try {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setUsername(username);

            model.addAttribute("messageDTO", null);
            model.addAttribute("accountDTO", accountDTO);

            return "register_success";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = {"", "/"})
    public String register(Model model, @Valid AccountDTO accountDTO, BindingResult bindingResult) {
        try {
            String redirectUrlSuccess = "";
            // validate
            registerValidator.validate(accountDTO, bindingResult);

            if (!bindingResult.hasErrors()) {
                // save
                Account account = accountService.register(accountDTO);
                if (account != null) {
                    redirectUrlSuccess = "/register/success?username=" + account.getUsername();
                    return "redirect:" + redirectUrlSuccess;
                }
            }
            model.addAttribute("messageDTO", new MessageDTO("error",
                    "Vui lòng kiểm tra lại thông tin!"));
            model.addAttribute("accountDTO", accountDTO);
            return "register";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/tutor", "/tutor/"})
    public String registerTutor(Model model) {
        try {
            Date date = new Date();
            String dateStr = DateUtil.convertDateToString(date, "yyyy-MM-dd");
            TutorDTO tutorDTO = new TutorDTO();
            tutorDTO.setBirthday(dateStr);
            List<TeachingClass> teachingClassList = teachingClassService.findAll();

            model.addAttribute("messageDTO", null);
            model.addAttribute("tutorDTO", tutorDTO);
            model.addAttribute("teachingClassDTOList", teachingClassMapper.toListDTO(teachingClassList));
            return "register_tutor";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/parent", "/parent/"})
    public String registerParent(Model model) {
        try {
            ParentDTO parentDTO = new ParentDTO();

            model.addAttribute("messageDTO", null);
            model.addAttribute("parentDTO", parentDTO);
            return "register_parent";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = {"/tutor/", "/tutor"})
    public String registerTutor(Model model, @Valid TutorDTO tutorDTO, BindingResult bindingResult) {
        try {
            String redirectUrlSuccess = "";
            // validate
            tutorValidator.validate(tutorDTO, bindingResult);
            if (!bindingResult.hasErrors()) {
                // save
                Tutor tutor = tutorMapper.toEntity(tutorDTO);

                if (tutorDTO.getIdPhotoMul() != null && !ObjectUtils.isEmpty(tutorDTO.getIdPhotoMul().getOriginalFilename())) {
                    FileDTO fileDTOBack = fileUploadService.uploadFile(tutorDTO.getIdPhotoMul());
                    tutor.setIdPhoto(fileDTOBack.getPath());
                }

                if (tutorDTO.getCardPhotoMul() != null && !ObjectUtils.isEmpty(tutorDTO.getCardPhotoMul().getOriginalFilename())) {
                    FileDTO fileDTOBack = fileUploadService.uploadFile(tutorDTO.getCardPhotoMul());
                    tutor.setCardPhoto(fileDTOBack.getPath());
                }

                if (tutorDTO.getDegreePhotoMul() != null && !ObjectUtils.isEmpty(tutorDTO.getDegreePhotoMul().getOriginalFilename())) {
                    FileDTO fileDTOBack = fileUploadService.uploadFile(tutorDTO.getDegreePhotoMul());
                    tutor.setDegreePhoto(fileDTOBack.getPath());
                }

                tutorService.registerTutor(tutor, tutorDTO);


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

                redirectUrlSuccess = "/register/success?username=" + tutorDTO.getAccountDTO().getFullName();
                return "redirect:" + redirectUrlSuccess;
            }
            List<TeachingClass> teachingClassList = teachingClassService.findAll();
            model.addAttribute("tutorDTO", tutorDTO);
            model.addAttribute("teachingClassDTOList", teachingClassMapper.toListDTO(teachingClassList));
            return "register_tutor";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = {"/parent/", "/parent"})
    public String registerParent(Model model, @Valid ParentDTO parentDTO, BindingResult bindingResult) {
        try {
            String redirectUrlSuccess = "";
            // validate
            parentValidator.validate(parentDTO, bindingResult);

            if (!bindingResult.hasErrors()) {
                // save
                Parent parent = parentMapper.toEntity(parentDTO);

                parentService.registerParent(parent, parentDTO);
                redirectUrlSuccess = "/register/success?username=" + parentDTO.getAccountDTO().getFullName();
                return "redirect:" + redirectUrlSuccess;
            }
            model.addAttribute("tutorDTO", parentDTO);
            return "register_parent";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
