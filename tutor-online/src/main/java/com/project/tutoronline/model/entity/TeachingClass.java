package com.project.tutoronline.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "teaching_class")
public class TeachingClass extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

}
