package com.blog.blog_project.controllers;

import com.blog.blog_project.payload.request.LoginRequest;
import com.blog.blog_project.payload.request.SignupRequest;
import com.blog.blog_project.payload.response.JwtResponse;
import com.blog.blog_project.payload.response.MessageResponse;
import com.blog.blog_project.repositories.UserRepository;
import com.blog.blog_project.security.jwt.JwtUtils;
import com.blog.blog_project.services.AuthService;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    RefreshTokenRequest refreshTokenRequest;



    @PostMapping("signup")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) throws ZCWBlogException{
        authService.signup(signupRequest);
        return new ResponseEntity<>("User Registration Successful", HttpStatus.CREATED);
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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        authService.login(loginRequest);
        return new ResponseEntity<>("User Authentication Successful!", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken()):
        return ResponseEntity.status(HttpStatus.ok)).body("Refresh Token Deleted Successfully!");
    }

}
