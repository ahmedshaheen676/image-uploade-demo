package com.shaheen.uploadImageDemo.controller;

import com.shaheen.uploadImageDemo.model.ImageFile;
import com.shaheen.uploadImageDemo.service.ImageFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        return "redirect:/image/allImages";
    }

    @GetMapping(value = "/byId")
    public ResponseEntity<ByteArrayResource> getImage(@RequestParam("imageId") String imageId) {
        System.out.println(imageId);
        Optional<ImageFile> optionalImageFile = imageFileService.findById(Long.valueOf(imageId));
        if (optionalImageFile.isPresent()) {
            ImageFile imageFile = optionalImageFile.get();
            try (FileInputStream fileInputStream = new FileInputStream(imageFile.getUrl())) {
                imageFile.setContent(fileInputStream.readAllBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.parseMediaType("image/" + imageFile.getImageType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageFile.getName() + "\"")
                    .body(new ByteArrayResource(imageFile.getContent()));
        }
        return null;
    }


}
