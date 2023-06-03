package com.project.tutoronline.controller.front;

import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.model.dto.PostTimeTeachingDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Parent;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.entity.PostTimeTeaching;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.model.mapper.PostTimeTeachingMapper;
import com.project.tutoronline.model.mapper.TimeTeachingMapper;
import com.project.tutoronline.service.*;
import com.project.tutoronline.validator.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/front/post")
public class JobApplicationController {

    private static final String REDIRECT_URL = "/back/post";

    @Autowired
    private PostService postService;

    @Autowired
    private PostValidator postValidator;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ParentService parentService;

    @Autowired
    private TimeTeachingService timeTeachingService;

    @Autowired
    private TimeTeachingMapper timeTeachingMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeachingClassService teachingClassService;

    @Autowired
    private PostTimeTeachingService postTimeTeachingService;

    @Autowired
    private PostTimeTeachingMapper postTimeTeachingMapper;

    @GetMapping(value = {"/form", "/form/"})
    public String postHome(Model model, Authentication authentication) {
        CustomAccountDetails customAccountDetails = (CustomAccountDetails) authentication.getPrincipal();
        Account account = customAccountDetails.getAccount();

        Parent parent = parentService.findByAccount(account);

        PostDTO postDTO = new PostDTO();
        postDTO.setAccountId(account.getId());
        postDTO.setFullName(parent.getAccount().getFullName());
        model.addAttribute("messageDTO", null);
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("courseListDTO", courseService.findAll());
        model.addAttribute("timeTeachingListDTO", timeTeachingMapper.toListDTO(timeTeachingService.findAll()));
        model.addAttribute("teachingClassListDTO", teachingClassService.findAll());
        return "/front/job_post";
    }

    @GetMapping("/form/{id}")
    public String detail(Model model, @PathVariable long id) {
        Post post = postService.findById(id);
        List<PostTimeTeaching> postTimeTeachingList = postTimeTeachingService.findByPost(post);
        model.addAttribute("postDTO", postMapper.toDTO(post));
        model.addAttribute("postTimeTeachingList", postTimeTeachingMapper.toListDTO(postTimeTeachingList));
        return "/front/job_detail";
    }

    @PostMapping(value = "/form")
    public String save(Model model, PostDTO postDTO, BindingResult bindingResult) {
        try {
            String redirectUrl = "";
            // validate
            postValidator.validate(postDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "/front/job_post";
            } else {
                // save
                postDTO.setProgress("Đang Xét Duyệt");
                postDTO.setStatus(true);
                Post post = postService.save(postMapper.toEntity(postDTO));

                if (post != null) {
                    if (postDTO.getId() == 0) {
                        postDTO.getTimeTeachingId().forEach(timeTeachingId -> {
                            PostTimeTeachingDTO postTimeTeachingDTO = new PostTimeTeachingDTO();
                            postTimeTeachingDTO.setPostId(post.getId());
                            postTimeTeachingDTO.setTimeTeachingId(Long.parseLong(timeTeachingId));
                            postTimeTeachingDTO.setStatus(true);
                            postTimeTeachingService.save(postTimeTeachingMapper.toEntity(postTimeTeachingDTO));
                        });
                    }
                    redirectUrl = "/front/post/form/" + post.getId() + "?action=save&status=success";
                } else {
                    redirectUrl = "/front/post/form/" + "?action=error";
                }

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

}
