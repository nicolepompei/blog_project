package com.blog.blog_project.controllers;


import com.blog.blog_project.entities.BlogPost;
import com.blog.blog_project.repositories.TagRepository;
import com.blog.blog_project.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class BlogPostController {
    private BlogPostService blogPostService;
    @Autowired
    public BlogPostController(BlogPostService blogPostService){
        this.blogPostService = blogPostService;
    }


    @GetMapping("/blog/getall")
    public ResponseEntity<Iterable<BlogPost>> getAll(){
        return new ResponseEntity<Iterable<BlogPost>>(blogPostService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/blog/tag/{tag}")
    public ResponseEntity<Iterable<BlogPost>> getAllByTag(@PathVariable String tag){
        return new ResponseEntity<>(blogPostService.getAllByTag(tag), HttpStatus.OK);
    }

    @GetMapping("/blog/id/{id}")
    public ResponseEntity<Iterable<BlogPost>> getAllById(@PathVariable Long id){
        return new ResponseEntity<Iterable<BlogPost>>(blogPostService.getAllById(id), HttpStatus.OK);
    }



    @DeleteMapping("/blog/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        blogPostService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/blog/")
    public ResponseEntity<BlogPost> createBlogPost(@Valid @RequestBody BlogPost blogPost){
        return new ResponseEntity<BlogPost>(blogPostService.createBlogPost(blogPost),HttpStatus.CREATED);
    }

    @PutMapping("/blog/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable Long id, @Valid @RequestBody BlogPost blogPost){
        return new ResponseEntity<BlogPost>(blogPostService.updateBlogPost(blogPost, id),HttpStatus.OK);
    }



    //--------------------------Validation error handling----------------------------
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
//    @GetMapping("/blog/userName/{userName}")
//    public ResponseEntity<Iterable<BlogPost>> getAllByUserName(@PathVariable String userName){
//        return new ResponseEntity<Iterable<BlogPost>>(blogPostService.getAllByUserName(userName), HttpStatus.OK);
//    }
}
