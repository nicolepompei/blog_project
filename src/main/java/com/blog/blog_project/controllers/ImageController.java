package com.blog.blog_project.controllers;

import com.blog.blog_project.services.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    private ImageUploadService imageUploadService;

    @Autowired
    public ImageController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @PostMapping("/upload/local")
    public void uploadLocal(@RequestParam("file")MultipartFile file) {
        imageUploadService.uploadToLocal(file);
    }

    @PostMapping("/upload/db")
    public void uploadDb(@RequestParam("file")MultipartFile file) {

    }
}
