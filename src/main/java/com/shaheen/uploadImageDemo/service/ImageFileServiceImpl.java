package com.shaheen.uploadImageDemo.service;

import com.shaheen.uploadImageDemo.model.ImageFile;
import com.shaheen.uploadImageDemo.repository.ImageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
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
        String imageType = getImageType(multipartImageFile.getOriginalFilename());
        String imageName = getImageName(multipartImageFile.getOriginalFilename());
        try {
            imageFile.setName(imageName);
            imageFile.setImageType(imageType);
            imageFile.setContent(multipartImageFile.getBytes());
            String path = "images/" + imageName + "_" + System.currentTimeMillis() + "." + imageType;
            imageFile.setUrl(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile;
    }

    private String getImageType(String originalFileName) {
        int indexOfImageType = originalFileName.lastIndexOf(".");
        return originalFileName.substring(indexOfImageType + 1);
    }

    private String getImageName(String originalFileName) {
        int indexOfImageType = originalFileName.lastIndexOf(".");
        return originalFileName.substring(0, indexOfImageType);
    }
}
