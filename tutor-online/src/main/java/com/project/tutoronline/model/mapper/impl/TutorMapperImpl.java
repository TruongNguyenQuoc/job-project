package com.project.tutoronline.model.mapper.impl;

import com.project.tutoronline.model.dto.AccountDTO;
import com.project.tutoronline.model.dto.TutorDTO;
import com.project.tutoronline.model.entity.Tutor;
import com.project.tutoronline.model.entity.TutorTeachingClass;
import com.project.tutoronline.model.mapper.AccountMapper;
import com.project.tutoronline.model.mapper.TutorMapper;
import com.project.tutoronline.service.TutorService;
import com.project.tutoronline.service.TutorTeachingClassService;
import com.project.tutoronline.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TutorMapperImpl implements TutorMapper {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TutorService tutorService;

    @Autowired
    private TutorTeachingClassService tutorTeachingClassService;

    @Override
    public TutorDTO toDTO(Tutor tutor) {
        if(tutor == null) return null;

        TutorDTO tutorDTO = new TutorDTO();
        tutorDTO.setId(tutor.getId());
        tutorDTO.setPhone(tutor.getPhone());
        tutorDTO.setAvatar(tutor.getAvatar());
        tutorDTO.setAddress(tutor.getAddress());
        tutorDTO.setBirthday(DateUtil.convertDateToString(tutor.getBirthday(), "dd-MM-yyyy"));
        tutorDTO.setOrigin(tutor.getOrigin());
        tutorDTO.setIdNumber(tutor.getIdNumber());
        tutorDTO.setIdPhoto(tutor.getIdPhoto());
        tutorDTO.setCardPhoto(tutor.getCardPhoto());
        tutorDTO.setDegreePhoto(tutor.getDegreePhoto());
        tutorDTO.setSchool(tutor.getSchool());
        tutorDTO.setSpecialization(tutor.getSpecialization());
        tutorDTO.setYearCollege(tutor.getYearCollege());
        tutorDTO.setLevel(tutor.getLevel());
        tutorDTO.setStatus(tutor.isStatus());

        if (tutor.getAccount() != null) {
            AccountDTO accountDTO = accountMapper.toDTO(tutor.getAccount());
            tutorDTO.setAccountDTO(accountDTO);
            tutorDTO.setAccountId(tutor.getAccount().getId());
        }

        List<TutorTeachingClass> tutorTeachingClassList = tutorTeachingClassService.findByTutor(tutor);
        List<String> teachingClassIdList = new ArrayList<>();
        tutorTeachingClassList.forEach(
                element -> {
                    teachingClassIdList.add(String.valueOf(element.getTeachingClass().getId()));
                }
        );
        tutorDTO.setTeachingClassIdList(teachingClassIdList);

        return tutorDTO;
    }

    @Override
    public List<TutorDTO> toListDTO(List<Tutor> tutors) {
        if(tutors == null) return null;

        List<TutorDTO> result = new ArrayList<>();
        tutors.forEach(tutor -> result.add(toDTO(tutor)));

        return result;
    }

    @Override
    public Tutor toEntity(TutorDTO tutorDTO) {
        if(tutorDTO == null) return null;

        Tutor tutor = tutorService.findById(tutorDTO.getId());
        if (tutor == null) tutor = new Tutor();

        tutor.setPhone(tutorDTO.getPhone());
        tutor.setAvatar(tutorDTO.getAvatar());
        tutor.setAddress(tutorDTO.getAddress());
        tutor.setBirthday(DateUtil.convertStringToDate(tutorDTO.getBirthday(), "dd-MM-yyyy"));
        tutor.setOrigin(tutorDTO.getOrigin());
        tutor.setIdNumber(tutorDTO.getIdNumber());

//        tutor.setIdPhoto(tutorDTO.getIdPhoto());
//        tutor.setCardPhoto(tutorDTO.getCardPhoto());
//        tutor.setDegreePhoto(tutorDTO.getDegreePhoto());

        tutor.setSchool(tutorDTO.getSchool());
        tutor.setSpecialization(tutorDTO.getSpecialization());
        tutor.setYearCollege(tutorDTO.getYearCollege());
        tutor.setLevel(tutorDTO.getLevel());
        tutor.setStatus(tutorDTO.isStatus());

        return tutor;
    }
}
