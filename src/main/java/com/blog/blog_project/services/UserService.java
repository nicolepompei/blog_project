package com.blog.blog_project.services;

import com.blog.blog_project.entities.User;
import com.blog.blog_project.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static java.time.Instant.now;

@Slf4j
@Service
public class UserService {



    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        User newUser = new User();
        newUser.setUsername((user.getUsername()));
        newUser.setEmail((user.getEmail()));
        newUser.setPassword(user.getPassword());
        newUser.setTimestamp(now());

        userRepository.save(newUser);
        log.info("User Registered Successfully, Sending Authentication Email");
        return newUser;
    }



    //TODO: Error handling.
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(RuntimeException::new);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
