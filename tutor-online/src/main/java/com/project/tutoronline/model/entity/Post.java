package com.project.tutoronline.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "teaching_class_id")
    private TeachingClass teachingClass;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "code")
    private String code;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "price")
    private double price;

    @Column(name = "number_of_session")
    private int numberOfSession;

    @Column(name = "information")
    private String information;

    @Column(name = "requirement")
    private String requirement;

    @Column(name = "mode")
    private String mode;

    @Column(name = "progress")
    private String progress;

    @Column(name = "status")
    private boolean status;

}
