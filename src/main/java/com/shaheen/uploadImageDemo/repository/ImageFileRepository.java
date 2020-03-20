package com.shaheen.uploadImageDemo.repository;

import com.shaheen.uploadImageDemo.model.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageFileRepository extends JpaRepository<ImageFile,Long> {
}
