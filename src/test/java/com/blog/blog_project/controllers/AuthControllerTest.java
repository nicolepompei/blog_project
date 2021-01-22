package com.blog.blog_project.controllers;

import com.blog.blog_project.exceptions.ZcwBlogException;
import com.blog.blog_project.payload.request.LoginRequest;
import com.blog.blog_project.payload.request.SignupRequest;
import com.blog.blog_project.payload.response.AuthenticationResponse;
import com.blog.blog_project.payload.response.MessageResponse;
import com.blog.blog_project.payload.response.PostResponse;
import com.blog.blog_project.security.jwt.AuthEntryPointJwt;
import com.blog.blog_project.security.jwt.JwtUtils;
import com.blog.blog_project.security.services.UserDetailsImpl;
import com.blog.blog_project.security.services.UserDetailsServiceImpl;
import com.blog.blog_project.services.AuthService;
import com.blog.blog_project.services.BlogPostService;
import com.blog.blog_project.services.RefreshTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import static java.util.Arrays.asList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc

public class AuthControllerTest {


    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    private AuthService authService;

    @MockBean
    JwtUtils jwtUtils;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    RefreshTokenService refreshTokenService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void before(){
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    public void after(){
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should return a 201 status for authenticated user")
    public void signinTest() throws Exception, ZcwBlogException {

        AuthenticationResponse authenticationResponse = new AuthenticationResponse("12345678", "12121212", Instant.now(), "pompy");

        final String token = jwtUtils.generateTokenWithUserName(authenticationResponse.getUsername());
        Mockito.when(authService.login(any())).thenReturn(authenticationResponse);
        mockMvc.perform(post("/api/signin")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return a 201 status for signup")
    public void signupTest() throws Exception, ZcwBlogException {
        SignupRequest signupRequest = new SignupRequest("pompy", "pompy@email.com", "password");

        final String token = jwtUtils.generateTokenWithUserName(signupRequest.getUsername());

        ResponseEntity expectedResponseEntity = ResponseEntity.ok()
                .body((new MessageResponse("User successfully Created!")));

        Mockito.when(authService.signup(signupRequest)).thenReturn(expectedResponseEntity);
        mockMvc.perform(post("/api/signup")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return a 201 status on logout")
    public void logOutTest() throws Exception, ZcwBlogException {
        ResponseEntity expectedResponseEntity = ResponseEntity.ok()
                .body((new MessageResponse("Refresh Token Deleted Successfully!")));

        UserDetails user = new User("test", "password", new ArrayList<>());

        final String token = jwtUtils.generateTokenWithUserName(user.getUsername());
        Mockito.when(refreshTokenService.deleteRefreshToken(any())).thenReturn(expectedResponseEntity);
        mockMvc.perform(post("/api/logout")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return a 201 status on refresh token")
    public void refreshTokenTest() throws ZcwBlogException, Exception {
        UserDetails user = new User("test", "password", new ArrayList<>());
        final String token = jwtUtils.generateTokenWithUserName(user.getUsername());
        Mockito.when(userDetailsService.loadUserByUsername(any())).thenReturn(user);
        mockMvc.perform(post("/api/refresh/token")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void AuthenticatedTokenAccess() throws Exception, ZcwBlogException {
        UserDetails user = new User("test", "password", new ArrayList<>());
        String token = jwtUtils.generateTokenWithUserName(user.getUsername());
        Mockito.when(userDetailsService.loadUserByUsername(any())).thenReturn(user);
        mockMvc.perform(post("/posts")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }


}
