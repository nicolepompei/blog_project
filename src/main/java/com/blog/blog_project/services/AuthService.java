package com.blog.blog_project.services;

import com.blog.blog_project.entities.User;
import com.blog.blog_project.payload.request.LoginRequest;
import com.blog.blog_project.payload.request.SignupRequest;
import com.blog.blog_project.payload.response.JwtResponse;
import com.blog.blog_project.payload.response.MessageResponse;
import com.blog.blog_project.repositories.UserRepository;
import com.blog.blog_project.security.jwt.JwtUtils;
import com.blog.blog_project.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.springframework.http.ResponseEntity.*;

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
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RefreshTokenService refreshTokenService;



    public ResponseEntity<MessageResponse> signup (SignupRequest signupRequest) throws ZcwBlogException{
        if(userRepository.existsByUsername(signupRequest.getUsername())){
            return badRequest()
                    .body( new MessageResponse("Error: Username is already taken!"));
        }

        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User();
        user.setUsername((signupRequest.getUsername()));
        user.setEmail((signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        //user.setTimestamp(new LocalDateTime()); dont think this is needed

        userRepository.save(user);

        log.info("User Registered Successfully!");

        return new ResponseEntity<MessageResponse>(new MessageResponse("User successfully Created!"), HttpStatus.CREATED);
    }

    public JwtResponse login(LoginRequest loginRequest) throws ZcwBlogException{
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwt = jwtUtils.generateJwtToken(authenticate);

        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();

        JwtResponse jwtResponse = JwtResponse.builder()
                            .token(jwt)
                            .id(userDetails.getId())
                            .username(userDetails.getUsername())
                            .email(userDetails.getEmail())
                            .build();
        return jwtResponse;
    }


}
