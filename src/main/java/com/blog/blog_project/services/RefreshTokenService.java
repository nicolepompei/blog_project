package com.blog.blog_project.services;

import com.blog.blog_project.entities.RefreshToken;
import com.blog.blog_project.exceptions.ZcwBlogException;
import com.blog.blog_project.repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
@Service
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
      //  refreshToken.setCreatedDate(new LocalDateTime()); I don't think we need this

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String token) throws ZcwBlogException {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ZcwBlogException("Invalid Refresh Token"));
    }

    public void deleteRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }
}
