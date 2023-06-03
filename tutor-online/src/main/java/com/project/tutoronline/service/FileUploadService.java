package com.project.tutoronline.service;

import com.project.tutoronline.model.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {

    FileDTO uploadFile(MultipartFile multipartFile);

    List<FileDTO> uploadMultiFile(MultipartFile[] multipartFiles);

}
