package com.project.tutoronline.model.mapper.impl;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.CourseDTO;
import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.model.dto.TeachingClassDTO;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.entity.PostTimeTeaching;
import com.project.tutoronline.model.mapper.AccountMapper;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.service.*;
import com.project.tutoronline.utils.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapperImpl implements PostMapper {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeachingClassService teachingClassService;

    @Autowired
    private PostTimeTeachingService postTimeTeachingService;

    @Override
    public PostDTO toDTO(Post post) {
        if (post == null) return null;

        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setFullName(post.getFullName());
        postDTO.setAddress(post.getAddress());
        postDTO.setPrice(ValidatorUtil.formatNumber(post.getPrice()));
        postDTO.setNumberOfSession(String.valueOf(post.getNumberOfSession()));
        postDTO.setInformation(post.getInformation());
        postDTO.setRequirement(post.getRequirement());
        postDTO.setMode(post.getMode());
        postDTO.setProgress(post.getProgress());
        postDTO.setStatus(post.isStatus());

        if (post.getAccount() != null) {
            AccountDTO accountDTO = accountMapper.toDTO(post.getAccount());
            postDTO.setAccountDTO(accountDTO);
            postDTO.setAccountId(post.getAccount().getId());
        }

        if (post.getCourse() != null) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setId(post.getCourse().getId());
            courseDTO.setName(post.getCourse().getName());
            courseDTO.setStatus(post.getCourse().isStatus());
            postDTO.setCourseDTO(courseDTO);
            postDTO.setCourseId(post.getCourse().getId());
        }

        if (post.getTeachingClass() != null) {
            TeachingClassDTO teachingClassDTO = new TeachingClassDTO();
            teachingClassDTO.setId(post.getTeachingClass().getId());
            teachingClassDTO.setName(post.getTeachingClass().getName());
            teachingClassDTO.setStatus(post.getTeachingClass().isStatus());
            postDTO.setTeachingClassDTO(teachingClassDTO);
            postDTO.setTeachingClassId(post.getTeachingClass().getId());
        }

        List<PostTimeTeaching> postTimeTeachingList = postTimeTeachingService.findByPost(post);
        List<String> postTimeTeachingIdList = new ArrayList<>();
        postTimeTeachingList.forEach(
                element -> postTimeTeachingIdList.add(String.valueOf(element.getTimeTeaching().getId()))
        );
        postDTO.setTimeTeachingId(postTimeTeachingIdList);

        return postDTO;
    }

    @Override
    public List<PostDTO> toListDTO(List<Post> posts) {
        if (posts == null) return null;

        List<PostDTO> result = new ArrayList<>();
        posts.forEach(post -> result.add(toDTO(post)));

        return result;
    }

    @Override
    public Post toEntity(PostDTO postDTO) {
        if(postDTO == null) return null;

        Post post = postService.findById(postDTO.getId());

        if (post == null) post = new Post();

        post.setId(postDTO.getId());
        post.setFullName(postDTO.getFullName());
        post.setAddress(postDTO.getAddress());
        post.setPrice(ValidatorUtil.formatNumber(postDTO.getPrice()));
        post.setNumberOfSession(Integer.parseInt(postDTO.getNumberOfSession()));
        post.setInformation(postDTO.getInformation());
        post.setRequirement(postDTO.getRequirement());
        post.setMode(postDTO.getMode());
        post.setProgress(postDTO.getProgress());
        post.setStatus(postDTO.isStatus());

        post.setAccount(accountService.findById(postDTO.getAccountId()));
        post.setCourse(courseService.findById(postDTO.getCourseId()));
        post.setTeachingClass(teachingClassService.findById(postDTO.getTeachingClassId()));

        return post;
    }

}
