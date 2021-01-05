package com.blog.blog_project.services;

import com.blog.blog_project.repositories.ImageUploadRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class ImageUploadService implements ImageUploadRepository {

    private String targetPath = "/Users/earl/dev/target/upload_";

    @Override
    public void uploadToLocal(MultipartFile file) {
        try {
            byte[] data = file.getBytes();
            Path path = Paths.get(targetPath + file.getOriginalFilename());
            Files.write(path, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
