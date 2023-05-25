package com.project.tutoronline.controller.back;

import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.model.dto.PostTimeTeachingDTO;
import com.project.tutoronline.model.entity.Account;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.entity.TimeTeaching;
import com.project.tutoronline.model.mapper.AccountMapper;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/back/post")
public class PostController {

    private static final String REDIRECT_URL = "/back/post";

    @Autowired
    private PostService postService;

    @Autowired
    private PostValidator postValidator;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private TimeTeachingService timeTeachingService;

    @Autowired
    private TimeTeachingMapper timeTeachingMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeachingClassService teachingClassService;

    @Autowired
    private PostTimeTeachingService postTimeTeachingService;

    @Autowired
    private PostTimeTeachingMapper postTimeTeachingMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Post> postList = postService.findAll();
            model.addAttribute("postList", postMapper.toListDTO(postList));
            return "back/post_list";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form"})
    public String form(Model model, Authentication authentication) {
        try {

            CustomAccountDetails customAccountDetails = (CustomAccountDetails) authentication.getPrincipal();
            Account account = accountService.findByUsername(customAccountDetails.getAccount().getUsername());

            PostDTO postDTO = new PostDTO();
            postDTO.setAccountDTO(accountMapper.toDTO(account));
            postDTO.setAccountId(account.getId());
            model.addAttribute("messageDTO", null);
            model.addAttribute("postDTO", postDTO);
            model.addAttribute("courseListDTO", courseService.findAll());
            model.addAttribute("timeTeachingListDTO", timeTeachingService.findAll());
            model.addAttribute("teachingClassListDTO", teachingClassService.findAll());
            return "back/post_form";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form/{id}"})
    public String form(Model model, @PathVariable long id,
                       @RequestParam(required = false) String action,
                       @RequestParam(required = false) String status) {
        try {
            PostDTO postDTO = postMapper.toDTO(postService.findById(id));
            List<TimeTeaching> timeTeachingList = timeTeachingService.findAll();
            if (postDTO == null) {
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

            model.addAttribute("postDTO", postDTO);
            model.addAttribute("detail","detail");
            model.addAttribute("timeTeachingListDTO", timeTeachingMapper.toListDTO(timeTeachingList));
            return "back/post_detail";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
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
                return "back/post_form";
            } else {
                // save

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
                    redirectUrl = "/back/post/form/" + post.getId() + "?action=save&status=success";
                } else {
                    redirectUrl = "/back/post/form/" + "?action=error";
                }

                return "redirect:" + redirectUrl;
            }
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        Post post = postService.findById(id);
        post.setStatus(false);
        postService.save(post);
        return "redirect:" + REDIRECT_URL;
    }

}
