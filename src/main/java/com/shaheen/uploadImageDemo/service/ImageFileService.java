package com.shaheen.uploadImageDemo.service;

import com.shaheen.uploadImageDemo.model.ImageFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageFileService {
    Optional<ImageFile> findById(Long id);

    ImageFile save(MultipartFile imageFile);

    void delete(ImageFile imageFile);

    List<ImageFile> findAll();

}
