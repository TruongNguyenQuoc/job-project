package com.project.tutoronline.controller.back;

import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Autowired
//    private PostValidator postValidator;

    @Autowired
    private PostMapper postMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model) {
        try {
            List<Post> postList = postService.findAll();
            model.addAttribute("postList", postMapper.toListDTO(postList));
            return "back/post";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @GetMapping(value = {"/form"})
    public String form(Model model) {
        try {
            model.addAttribute("messageDTO", null);
            model.addAttribute("postDTO", new PostDTO());
            return "back/post";
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

            return "back/post";
        } catch (Exception ex) {
            return "redirect:" + REDIRECT_URL;
        }
    }

    @PostMapping(value = "/form")
    public String save(Model model, PostDTO postDTO, BindingResult bindingResult) {
        try {
            String redirectUrl = "";
            // validate
//            postValidator.validate(postDTO, bindingResult);

            if (bindingResult.hasErrors()) {
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "back/post";
            } else {
                // save
                Post post = postService.save(postMapper.toEntity(postDTO));
                if (post != null) {
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
