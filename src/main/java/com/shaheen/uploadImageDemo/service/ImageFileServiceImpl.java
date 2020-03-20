package com.shaheen.uploadImageDemo.service;

import com.shaheen.uploadImageDemo.model.ImageFile;
import com.shaheen.uploadImageDemo.repository.ImageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ImageFileServiceImpl implements ImageFileService {
    @Autowired
    private ImageFileRepository imageFileRepository;

    @Override
    public Optional<ImageFile> findById(Long id) {
        return imageFileRepository.findById(id);

    }

    @Override
    public ImageFile save(MultipartFile multipartImageFile) {

        ImageFile imageFile = getImageFile(multipartImageFile);

        try (FileOutputStream fileOutputStream =
                     new FileOutputStream(imageFile.getUrl())) {
            fileOutputStream.write(imageFile.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFileRepository.save(imageFile);
    }


    @Override
    public void delete(ImageFile imageFile) {

    }

    @Override
    public List<ImageFile> findAll() {
        return imageFileRepository.findAll();

    }

    /**
     * map MultipartFile to imageFile
     *
     * @param multipartImageFile
     * @return
     */
    private ImageFile getImageFile(MultipartFile multipartImageFile) {
        ImageFile imageFile = new ImageFile();

        imageFile.setName(multipartImageFile.getOriginalFilename());
        imageFile.setImageType(multipartImageFile.getContentType());
        try {
            imageFile.setContent(multipartImageFile.getBytes());
            String path = "images/" + imageFile.getName() + LocalDateTime.now();
            imageFile.setUrl(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }
}
