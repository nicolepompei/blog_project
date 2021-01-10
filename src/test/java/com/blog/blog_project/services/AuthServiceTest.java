package com.blog.blog_project.services;

import com.blog.blog_project.entities.User;
import com.blog.blog_project.entities.VerificationToken;
import com.blog.blog_project.exceptions.ZcwBlogException;
import com.blog.blog_project.payload.request.SignupRequest;
import com.blog.blog_project.payload.response.MessageResponse;
import com.blog.blog_project.repositories.UserRepository;
import com.blog.blog_project.repositories.VerificationTokenRepository;
import com.blog.blog_project.security.jwt.JwtUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;

    @Captor
    private ArgumentCaptor<ResponseEntity<?>> responseArgumentCaptor;

    private AuthService authService;

    @BeforeEach
    public void init(){
        authService = new AuthService(passwordEncoder, userRepository,
                authenticationManager, jwtUtils, refreshTokenService,
                verificationTokenRepository);
    }

    ///////   Signup Tests ///////////////////////////////////////////
    @Test // successful signin (the username and/or email are not taken)
    public void shouldReturnSuccessMessage() throws ZcwBlogException {

        SignupRequest mockSignupRequest = new SignupRequest("Chrispy", "passwerd", "chrispir@udel.edu");

        ResponseEntity expectedResponseEntity = ResponseEntity.ok(new MessageResponse("User successfully Created!"));

        Mockito.when(userRepository.existsByUsername(mockSignupRequest.getUsername())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(mockSignupRequest.getEmail())).thenReturn(false);

        ResponseEntity<?> actualResponseEntity = authService.signup(mockSignupRequest);
        Mockito.verify(passwordEncoder, Mockito.times(1))
                .encode(ArgumentMatchers.any(String.class));
        Mockito.verify(userRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(User.class));

        Assertions.assertThat(actualResponseEntity).isEqualTo(expectedResponseEntity);
    }

    @Test
    public void shouldReturnUsernameAlreadyInUse() throws ZcwBlogException {

        SignupRequest mockSignupRequest = new SignupRequest("Chrispy", "passwerd", "chrispir@udel.edu");

        ResponseEntity expectedResponseEntity = ResponseEntity.badRequest()
                .body((new MessageResponse("Error: Username is already taken!")));

        Mockito.when(userRepository.existsByUsername(mockSignupRequest.getUsername())).thenReturn(true);

        ResponseEntity actualResponseEntity = authService.signup(mockSignupRequest);

        Assertions.assertThat(expectedResponseEntity).isEqualTo(actualResponseEntity);

    }

    @Test
    public void shouldReturnEmailAlreadyInUse() throws ZcwBlogException {

        SignupRequest mockSignupRequest = new SignupRequest("Chrispy", "passwerd", "chrispir@udel.edu");

        ResponseEntity expectedResponseEntity = ResponseEntity.badRequest()
                .body((new MessageResponse("Error: Email is already in use!")));

        Mockito.when(userRepository.existsByUsername(mockSignupRequest.getUsername())).thenReturn(false);
        Mockito.when(userRepository.existsByEmail(mockSignupRequest.getEmail())).thenReturn(true);

        ResponseEntity actualResponseEntity = authService.signup(mockSignupRequest);

        Assertions.assertThat(expectedResponseEntity).isEqualTo(actualResponseEntity);

    }
    /////// generateVerificationToken tests

/*
    @Test
    public void shouldSaveVerificationToken(){
        VerificationToken mockToken = new VerificationToken();
        authService.generateVerificationToken(new User());
        Mockito.verify(verificationTokenRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(VerificationToken.class));
    }
    */




}