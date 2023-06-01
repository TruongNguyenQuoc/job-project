package com.project.tutoronline.validator;

import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.utils.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class ReportPostValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PostDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        PostDTO postDTO = (PostDTO) target;

        if (postDTO.getStartDate() != null && !postDTO.getStartDate().trim().isEmpty() &&
                postDTO.getEndDate() != null && !postDTO.getEndDate().trim().isEmpty()) {
            Date dateStart = DateUtil.convertStringToDate(postDTO.getStartDate(), "yyyy-MM-dd");
            Date dateEnd = DateUtil.convertStringToDate(postDTO.getEndDate(), "yyyy-MM-dd");
            boolean isStartBeforeEnd = DateUtil.compareStartDateEndDate(dateStart, dateEnd);
            if (!isStartBeforeEnd) {
                errors.rejectValue("startDate", "Thời gian bắt đầu lớn hơn thời gian kết thúc",
                        "Thời gian bắt đầu lớn hơn thời gian kết thúc");
            }
        }
    }
}
