package com.blog.blog_project.controllers;

import com.blog.blog_project.exceptions.ZcwBlogException;
import com.blog.blog_project.payload.request.LoginRequest;
import com.blog.blog_project.payload.request.RefreshTokenRequest;
import com.blog.blog_project.payload.request.SignupRequest;
import com.blog.blog_project.payload.response.AuthenticationResponse;
import com.blog.blog_project.payload.response.JwtResponse;
import com.blog.blog_project.payload.response.MessageResponse;
import com.blog.blog_project.repositories.UserRepository;
import com.blog.blog_project.security.jwt.JwtUtils;
import com.blog.blog_project.services.AuthService;
import com.blog.blog_project.services.RefreshTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// @CrossOrigin(origins = "http://localhost:4200")
@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    RefreshTokenService refreshTokenService;




    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) throws ZcwBlogException {
      return  authService.signup(signupRequest);
    }

    /**
     * authenticate {username, passowrd}
     * update SecurityContext using Authentication object
     * generate JWT
     * get UserDetails from Authentication object
     * response contains JWT and UserDetails data
     * @param loginRequest
     * @return
     */
    @PostMapping("/signin")
    public AuthenticationResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws ZcwBlogException{
        log.info("user signin controller executing: user signed in");
       return authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws ZcwBlogException{
        log.info("user logout controller executing: user logged out");
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return new ResponseEntity<>("Refresh Token Deleted Successfully!", HttpStatus.OK);
    }


    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws  ZcwBlogException {
        log.info("refresh tokens controller executing: token refreshed");
        return authService.refreshToken(refreshTokenRequest);
    }

}
