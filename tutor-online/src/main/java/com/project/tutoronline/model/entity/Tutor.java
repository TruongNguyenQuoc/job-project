package com.project.tutoronline.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "tutor")
public class Tutor extends BaseEntity {

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "address")
    private String address;

    @Column(name = "birthday")
    private int birthday;

    @Column(name = "origin")
    private String origin;

    @Column(name = "id_number")
    private String idNumber;

    @Column(name = "id_photo")
    private String idPhoto;

    @Column(name = "card_photo")
    private String cardPhoto;

    @Column(name = "degree_photo")
    private String degreePhoto;

    @Column(name = "school")
    private String school;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "year_college")
    private String yearCollege;

    @Column(name = "level")
    private String level;

    @Column(name = "teaching_class_id")
    private String teachingClass;

    @Column(name = "status")
    private boolean status;

}