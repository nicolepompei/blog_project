package com.blog.blog_project.controllers;


import com.blog.blog_project.entities.User;
import com.blog.blog_project.services.UserService;
import lombok.AllArgsConstructor;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final Logger LOG =  LoggerFactory.getLogger(UserController.class);
    @Autowired
    private final UserService userService;

    //======================== Create User ======================
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user){
        LOG.info("creating a user: {}", user);
        //no validation check for if a user already exists, this will happen with authentication
        userService.createUser(user);
        UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //==========================GET USER BY ID  =========================
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id){
        LOG.info("getting user with id: {}", id);
        User user = userService.findUserById(id);

        if(user == null){
            LOG.info("person with id {} not found", id);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    //========================GET ALL USERS========================
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<User>> getUserList(){
        LOG.info("Getting all users");
        List<User> userList = userService.findAll();
        if(userList == null || userList.isEmpty()){
            LOG.info("no users found");
            return new ResponseEntity<Iterable<User>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Iterable<User>>(userList, HttpStatus.OK);
    }


    //==================Get User By User Name ====================
    @RequestMapping(value = {"/username/{username}"}, method = RequestMethod.GET)
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username){
        LOG.info("getting user with username: {}", username);

        User user = userService.findByUsername(username);

        if(user == null){
            LOG.info("person with username {} not found", username);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    //================== Validation exception handler ====================

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    //findByEmail???????
    /*
    //==================Get User By Email Address ====================

    //seems like a bad idea to expose a users email address as an endpoint...
    @RequestMapping(value = {"emailaddress"}, method = RequestMethod.GET)
    public ResponseEntity getUserByEmailAddress(@PathVariable("emailaddress") String emailaddress){
        LOG.info("getting user with email address: {}", emailaddress);

        User user = userService.findByEmailAddress(emailaddress);

        if(user == null){
            LOG.info("person with email address {} not found", emailaddress);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

     */


}
