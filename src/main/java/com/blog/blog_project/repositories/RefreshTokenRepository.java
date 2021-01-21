package com.blog.blog_project.repositories;

import com.blog.blog_project.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface RefreshTokenRepository  extends JpaRepository<RefreshToken, Long> {

    Optional<ResponseEntity> deleteByToken(String token);

    Optional<RefreshToken> findByToken(String token);

    RefreshToken save(RefreshToken refreshToken);
}
