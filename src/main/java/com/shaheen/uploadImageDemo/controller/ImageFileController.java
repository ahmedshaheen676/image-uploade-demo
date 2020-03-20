package com.shaheen.uploadImageDemo.controller;

import com.shaheen.uploadImageDemo.model.ImageFile;
import com.shaheen.uploadImageDemo.service.ImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping("/image")
public class ImageFileController {
    @Autowired
    private ImageFileService imageFileService;

    @GetMapping(value = {"", "/"})
    public String getImageView() {
        return "uploadImage";
    }

    @GetMapping(value = {"/allImages"})
    public String viewAllImages(Model model) {
        List<ImageFile> imageFileList = imageFileService.findAll();
        model.addAttribute("imageFileList", imageFileList);
        return "allImages";
    }

    @PostMapping
    public String postImage(@RequestParam("imageFile") @Valid @NotNull MultipartFile imageFile) {
        imageFileService.save(imageFile);
        return "uploadImage";
    }
}
