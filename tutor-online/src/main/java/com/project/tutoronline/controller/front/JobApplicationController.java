package com.project.tutoronline.controller.front;

import com.project.tutoronline.model.dto.MessageDTO;
import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.model.dto.PostTimeTeachingDTO;
import com.project.tutoronline.model.dto.TutorDTO;
import com.project.tutoronline.model.entity.*;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.model.mapper.PostTimeTeachingMapper;
import com.project.tutoronline.model.mapper.TimeTeachingMapper;
import com.project.tutoronline.model.mapper.TutorMapper;
import com.project.tutoronline.service.*;
import com.project.tutoronline.utils.RandomUtil;
import com.project.tutoronline.validator.PostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/front/post")
public class JobApplicationController {

    private static final String REDIRECT_URL = "/back/post";

    private static final String POST_PENDING = "Đang Xét Duyệt";

    private static final String POST_APPROVED = "Lớp Chưa Giao";

    private static final String POST_CHECKING = "Đang Xác Nhận Gia Sư";

    private static final String POST_COMPLETE = "Lớp Đã Giao";

    private static final String POST_CANCER = "Đã Hủy";

    private static final int PAGE_SIZE = 9;

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

    @Autowired
    private JobService jobService;

    @Autowired
    private TutorMapper tutorMapper;

    @GetMapping(value = {"", "/"})
    public String list(Model model,
                       @RequestParam(value = "pageNumber", defaultValue = "1", required = false) int pageNumber) {
        return listByPage(model, pageNumber);
    }

    @GetMapping("/page/{currentPage}")
    public String listByPage(Model model, @PathVariable int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, PAGE_SIZE);
        Page<Post> postPageList = postService.findByPage(pageable);

        List<Post> postList = postPageList.getContent().stream()
                        .filter(post -> post.getProgress().equals(POST_APPROVED))
                        .collect(Collectors.toList());
        model.addAttribute("postDTOList", postMapper.toListDTO(postList));
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("pageTotal", postPageList.getTotalPages());
        return "/front/job_list";
    }

    @GetMapping(value = {"/form", "/form/"})
    public String create(Model model, Authentication authentication) {
        CustomAccountDetails customAccountDetails = (CustomAccountDetails) authentication.getPrincipal();
        Account account = customAccountDetails.getAccount();

        Parent parent = parentService.findByAccount(account);

        PostDTO postDTO = new PostDTO();
        postDTO.setPhone(account.getPhone());
        postDTO.setAccountId(account.getId());
        postDTO.setFullName(parent.getAccount().getFullName());
        model.addAttribute("messageDTO", null);
        model.addAttribute("postDTO", postDTO);
        model.addAttribute("courseListDTO", courseService.findAll());
        model.addAttribute("timeTeachingListDTO", timeTeachingMapper.toListDTO(timeTeachingService.findAll()));
        model.addAttribute("teachingClassListDTO", teachingClassService.findAll());
        return "/front/job_post";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable long id, @RequestParam(required = false) String action) {
        String redirectUrl = "";
        Post post = postService.findById(id);
        Job job = jobService.findByPost(post);
        if (post.getProgress().equals(POST_CHECKING) || post.getProgress().equals(POST_COMPLETE)) {
            TutorDTO tutorDTO = tutorMapper.toDTO(job.getTutor());
            model.addAttribute("tutorDTO", tutorDTO);
        }
        if (action != null && action.equals("refuse")) {
            post.setProgress(POST_APPROVED);
            job.setStatus(false);
            postService.save(post);
            jobService.save(job);
            redirectUrl = "/front/post/detail/" + id;
            return "redirect:" + redirectUrl;
        }
        if (action != null && action.equals("accept")) {
            post.setProgress(POST_COMPLETE);
            postService.save(post);
            redirectUrl = "/front/post/detail/" + id;
            return "redirect:" + redirectUrl;
        }

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
                model.addAttribute("courseListDTO", courseService.findAll());
                model.addAttribute("timeTeachingListDTO", timeTeachingMapper.toListDTO(timeTeachingService.findAll()));
                model.addAttribute("teachingClassListDTO", teachingClassService.findAll());
                model.addAttribute("status", "warning");
                model.addAttribute("messageDTO", new MessageDTO("save",
                        "Vui lòng kiểm tra lại thông tin!"));
                return "/front/job_post";
            } else {
                // save
                String codeId = RandomUtil.generateId(5);
                postDTO.setCode("BK " + codeId);
                postDTO.setProgress(POST_PENDING);
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
                    redirectUrl = "/front/post/detail/" + post.getId();
                } else {
                    redirectUrl = "/front/post/detail/" + "?action=error";
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
        post.setProgress(POST_CANCER);
        postService.save(post);
        String redirectUrl = "/front/profile/parent";
        return "redirect:" + redirectUrl;
    }

}
