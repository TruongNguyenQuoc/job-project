package com.project.tutoronline.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Column(name = "subject")
    private String subject;

    @Column(name = "address")
    private String address;

    @Column(name = "price")
    private String price;

    @Column(name = "number_of_session")
    private String numberOfSession;

    @Column(name = "information")
    private String information;

    @Column(name = "requirement")
    private String requirement;

    @Column(name = "mode")
    private String mode;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "time_teaching_id")
    private TimeTeaching timeTeaching;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "teaching_class_id")
    private TeachingClass teachingClass;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "status")
    private boolean status;

}
