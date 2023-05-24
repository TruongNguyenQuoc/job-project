package com.project.tutoronline.model.mapper.impl;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.model.dto.TeachingClassDTO;
import com.project.tutoronline.model.dto.TimeTeachingDTO;
import com.project.tutoronline.model.entity.Post;
import com.project.tutoronline.model.mapper.AccountMapper;
import com.project.tutoronline.model.mapper.PostMapper;
import com.project.tutoronline.service.AccountService;
import com.project.tutoronline.service.PostService;
import com.project.tutoronline.service.TeachingClassService;
import com.project.tutoronline.service.TimeTeachingService;
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
    private TimeTeachingService timeTeachingService;

    @Autowired
    private TeachingClassService teachingClassService;

    @Override
    public PostDTO toDTO(Post post) {
        if (post == null) return null;

        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setAddress(post.getAddress());
        postDTO.setPrice(ValidatorUtil.formatNumber(post.getPrice()));
        postDTO.setNumberOfSession(String.valueOf(post.getNumberOfSession()));
        postDTO.setInformation(post.getInformation());
        postDTO.setRequirement(post.getRequirement());
        postDTO.setMode(post.getMode());
        postDTO.setStatus(post.isStatus());

        if (post.getAccount() != null) {
            AccountDTO accountDTO = accountMapper.toDTO(post.getAccount());
            postDTO.setAccountDTO(accountDTO);
            postDTO.setAccountId(post.getAccount().getId());
        }

        if (post.getTimeTeaching() != null) {
            TimeTeachingDTO timeTeachingDTO = new TimeTeachingDTO();
            timeTeachingDTO.setId(post.getTimeTeaching().getId());
            timeTeachingDTO.setName(post.getTimeTeaching().getName());
            timeTeachingDTO.setStatus(post.getTimeTeaching().isStatus());
            postDTO.setTimeTeachingDTO(timeTeachingDTO);
            postDTO.setTimeTeachingId(post.getTimeTeaching().getId());
        }

        if (post.getTeachingClass() != null) {
            TeachingClassDTO teachingClassDTO = new TeachingClassDTO();
            teachingClassDTO.setId(post.getTeachingClass().getId());
            teachingClassDTO.setName(post.getTeachingClass().getName());
            teachingClassDTO.setStatus(post.getTeachingClass().isStatus());
            postDTO.setTeachingClassDTO(teachingClassDTO);
            postDTO.setTeachingId(post.getTeachingClass().getId());
        }

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
        post.setAddress(postDTO.getAddress());
        post.setPrice(ValidatorUtil.formatNumber(postDTO.getPrice()));
        post.setNumberOfSession(Integer.parseInt(postDTO.getNumberOfSession()));
        post.setInformation(postDTO.getAddress());
        post.setRequirement(postDTO.getAddress());
        post.setMode(postDTO.getAddress());
        post.setStatus(postDTO.isStatus());

        post.setAccount(accountService.findById(postDTO.getAccountId()));
        post.setTeachingClass(teachingClassService.findById(postDTO.getTeachingId()));
        post.setTimeTeaching(timeTeachingService.findById(postDTO.getTimeTeachingId()));

        return post;
    }

}
