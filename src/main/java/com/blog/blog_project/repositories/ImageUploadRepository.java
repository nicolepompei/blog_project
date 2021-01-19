package com.blog.blog_project.repositories;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadRepository {

    void uploadToLocal(MultipartFile file);
}
