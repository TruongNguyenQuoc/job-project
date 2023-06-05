package com.project.tutoronline.validator;

import com.project.tutoronline.model.dto.PostDTO;
import com.project.tutoronline.utils.ValidatorUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PostDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostDTO postDTO = (PostDTO) target;

        if (ValidatorUtil.isEmpty(postDTO.getAddress())) {
            errors.rejectValue("address", "Vui lòng nhập Địa Chỉ!",
                    "Vui lòng nhập Địa Chỉ!");
        }

        if (ValidatorUtil.isEmpty(postDTO.getInformation())) {
            errors.rejectValue("information", "Vui lòng nhập Thông tin Học Sinh!",
                    "Vui lòng nhập Thông tin Học Sinh!");
        }

        if (ValidatorUtil.isEmpty(postDTO.getNumberOfSession())) {
            errors.rejectValue("numberOfSession", "Vui lòng nhập Số buổi!",
                    "Vui lòng nhập Số buổi!");
        } else {
            if (!ValidatorUtil.isNumeric(postDTO.getNumberOfSession())) {
                errors.rejectValue("numberOfSession", "Số buổi phải là sô!",
                        "Số buổi phải là sô!");
            }
        }

        if (!ValidatorUtil.isNumeric(postDTO.getPhone())) {
            errors.rejectValue("phone", "Số Điện Thoại phải là số",
                    "Số Điện Thoại phải là số");
        } else if (!ValidatorUtil.checkPhone(postDTO.getPhone())) {
            errors.rejectValue("phone", "Số Điện Thoại phải có độ dài là 10!",
                    "Số Điện Thoại phải có độ dài là 10!");
        }

        if (ValidatorUtil.isEmpty(postDTO.getPrice())) {
            errors.rejectValue("price", "Vui lòng nhập Mức Lương!",
                    "Vui lòng nhập Mức Lương!");
        }
    }
}
