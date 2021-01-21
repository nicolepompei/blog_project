package com.blog.blog_project.services;

import com.blog.blog_project.entities.User;
import com.blog.blog_project.entities.VerificationToken;
import com.blog.blog_project.exceptions.ZcwBlogException;
import com.blog.blog_project.payload.request.LoginRequest;
import com.blog.blog_project.payload.request.RefreshTokenRequest;
import com.blog.blog_project.payload.request.SignupRequest;
import com.blog.blog_project.payload.response.AuthenticationResponse;
import com.blog.blog_project.payload.response.MessageResponse;
import com.blog.blog_project.repositories.UserRepository;
import com.blog.blog_project.repositories.VerificationTokenRepository;
import com.blog.blog_project.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;


    @Transactional
    public ResponseEntity<?> signup (SignupRequest signupRequest) throws ZcwBlogException{
        log.info("AuthService: signup called");
        if(userRepository.existsByUsername(signupRequest.getUsername())){
            log.info(String.format("AuthService: Error: Username %s taken", signupRequest.getUsername()));

            return new ResponseEntity<>("Error: Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            log.info(String.format("AuthService: Error: Email address %s already in use", signupRequest.getEmail()));
            return new ResponseEntity<>("Error: Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername((signupRequest.getUsername()));
        user.setEmail((signupRequest.getEmail()));
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        userRepository.save(user);

        log.info("AuthService: User Registered Successfully!");
        String token = generateVerificationToken(user);

        return ResponseEntity.ok(new MessageResponse("User successfully Created!"));
    }

    @VisibleForTesting // has to be package private, too
    private String generateVerificationToken(User user) {
        log.info(String.format("AuthService: generateVerificationToken called for user %s", user.getUsername()));
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now().plusMillis(jwtUtils.getJwtExpirationMs()));

        verificationTokenRepository.save(verificationToken);
        log.info("AuthService: Token generated successfully");
        return token;
    }

    public AuthenticationResponse login(LoginRequest loginRequest) throws ZcwBlogException {
        log.info("AuthService: login called");
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtUtils.generateJwtToken(authenticate);
        log.info("AuthService: User login successful!");
        return AuthenticationResponse.builder()
                    .authenticationToken(token)
                    .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                    .expiresAt(Instant.now().plusMillis(jwtUtils.getJwtExpirationMs()))
                    .username(loginRequest.getUsername())
                    .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) throws ZcwBlogException{
        log.info("AuthService: refereshToken called");
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtUtils.generateTokenWithUserName(refreshTokenRequest.getUsername());
        log.info("AuthService: refresh token successfully generated");
            return AuthenticationResponse.builder()
                    .authenticationToken(token)
                    .refreshToken(refreshTokenRequest.getRefreshToken())

                    .expiresAt(Instant.now().plusMillis(jwtUtils.getJwtExpirationMs()))
                    .username(refreshTokenRequest.getUsername())
                    .build();
    }


    @Transactional
    public User getCurrentUser(){
        log.info("AuthService: getCurrentUser called");
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                 .getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found - " + principal.getUsername()));
    }


}
