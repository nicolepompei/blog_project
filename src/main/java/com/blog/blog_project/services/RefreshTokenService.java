package com.blog.blog_project.services;

import com.blog.blog_project.entities.RefreshToken;
import com.blog.blog_project.exceptions.ZcwBlogException;
import com.blog.blog_project.repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
@Slf4j
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    RefreshToken generateRefreshToken(){
        log.info("generateRefreshToken called");
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token) throws ZcwBlogException {
        log.info("validateRefreshToken called");
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ZcwBlogException("Invalid Refresh Token"));
    }

    public ResponseEntity<?> deleteRefreshToken(String token) throws ZcwBlogException {
        log.info("Refresh token successfully deleted. User logged out!");
       return refreshTokenRepository.deleteByToken(token)
               .orElseThrow(() -> new ZcwBlogException("Invalid Refresh Token; token cannot be deleted"));

    }
}
