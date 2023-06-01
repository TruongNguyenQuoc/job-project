package com.project.tutoronline.controller.back;

import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.service.PostService;
import com.project.tutoronline.utils.DateUtil;
import com.project.tutoronline.utils.ValidatorUtil;
import com.project.tutoronline.validator.ReportPostValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/back/report-post")
public class ReportPostController {

    private static final String APPROVED_PROGRESS = "Lớp Chưa Giao";

    private static final String COMPLETED_PROGRESS = "Lớp Đã Giao";

    @Autowired
    private PostService postService;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private ReportPostValidator reportPostValidator;

    @GetMapping(value = {"/", ""})
    public String list(Model model) {
        Date date = new Date();
        String dateStr = DateUtil.convertDateToString(date, "yyyy-MM-dd");

        PostDTO postDTO = new PostDTO();
        postDTO.setStartDate(dateStr);
        postDTO.setEndDate(dateStr);

        setModel(model, postDTO);

        return "back/repost_post";
    }

    @GetMapping("/search")
    public String search(Model model, PostDTO postDTO, BindingResult bindingResult) {
        reportPostValidator.validate(postDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            Date date = new Date();
            String dateStr = DateUtil.convertDateToString(date, "yyyy-MM-dd");
            postDTO.setStartDate(dateStr);
            postDTO.setEndDate(dateStr);
            setModel(model, postDTO);
            model.addAttribute("errorList", ValidatorUtil.toErrors(bindingResult.getFieldErrors()));
            return "back/repost_post";
        }

        setModel(model, postDTO);

        return "back/repost_post";
    }

    private void setModel(Model model, PostDTO postDTO){
        List<PostDTO> posts = postMapper.toListDTO(postService.searchPost(postDTO));

        List<PostDTO> postApproved = posts.stream()
                .filter(object -> object.getProgress().equals(APPROVED_PROGRESS))
                .toList();

        List<PostDTO> postCompleted = posts.stream()
                .filter(object -> object.getProgress().equals(COMPLETED_PROGRESS))
                .toList();

        double postTotalPrice = posts.stream()
                .mapToDouble(i -> ValidatorUtil.formatNumber(i.getPrice()))
                .sum();


        model.addAttribute("postDTOList", posts);
        model.addAttribute("postApproved", postApproved.size());
        model.addAttribute("postCompleted", postCompleted.size());
        model.addAttribute("postTotalPrice", postTotalPrice);
        model.addAttribute("postDTO", postDTO);
    }
    
}
