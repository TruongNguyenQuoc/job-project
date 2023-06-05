package com.project.tutoronline.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailDTO <T> {

    private T data;
    private String time;
    private int minutes;

}
