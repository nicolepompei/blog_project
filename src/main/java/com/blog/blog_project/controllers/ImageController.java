package com.blog.blog_project.controllers;

import com.amazonaws.services.s3.AmazonS3Client;
import com.blog.blog_project.aws_config_services.AWSs3Client;
import com.blog.blog_project.services.ImageUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@Slf4j
public class ImageController {

    @Autowired
    private ImageUploadService imageUploadService;

    private AWSs3Client s3Client;


    @Autowired
    public ImageController(AWSs3Client s3Client) {
        this.s3Client = s3Client;
    }


    @PostMapping("/uploadS3")
    public ResponseEntity<String> uploadFileToS3(@RequestParam(value= "image")MultipartFile file) {
        log.info("upload to S3 controller executing: image uploading");
        return new ResponseEntity<>(s3Client.uploadFile(file), HttpStatus.ACCEPTED);

    }
}
