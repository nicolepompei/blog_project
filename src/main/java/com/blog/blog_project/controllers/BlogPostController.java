package com.blog.blog_project.controllers;


import com.blog.blog_project.entities.BlogPost;
import com.blog.blog_project.services.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody BlogPost blogPost){
        return new ResponseEntity<BlogPost>(blogPostService.createBlogPost(blogPost),HttpStatus.CREATED);
    }

    @PutMapping("/blog/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable Long id,@RequestBody BlogPost blogPost){
        return new ResponseEntity<BlogPost>(blogPostService.updateBlogPost(blogPost, id),HttpStatus.OK);
    }


//    @GetMapping("/blog/userName/{userName}")
//    public ResponseEntity<Iterable<BlogPost>> getAllByUserName(@PathVariable String userName){
//        return new ResponseEntity<Iterable<BlogPost>>(blogPostService.getAllByUserName(userName), HttpStatus.OK);
//    }
}