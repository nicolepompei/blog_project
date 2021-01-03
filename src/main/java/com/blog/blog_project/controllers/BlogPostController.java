//package com.blog.blog_project.controllers;
//
//
//import com.blog.blog_project.entities.BlogPost;
//import com.blog.blog_project.payload.request.PostRequest;
//import com.blog.blog_project.payload.response.PostResponse;
//import com.blog.blog_project.repositories.TagRepository;
//import com.blog.blog_project.services.BlogPostService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//@RequestMapping("/posts")
//@RestController
//public class BlogPostController {
//    private BlogPostService blogPostService;
//    @Autowired
//    public BlogPostController(BlogPostService blogPostService){
//        this.blogPostService = blogPostService;
//    }
//
//  @PostMapping
//    public ResponseEntity<BlogPost> createBlogPost(@RequestBody BlogPost blogPost){
//        return new ResponseEntity<BlogPost>(blogPostService.createBlogPost(blogPost),HttpStatus.CREATED);
//    }
//    @GetMapping
//    public ResponseEntity<Iterable<BlogPost>> getAll(){
//        return new ResponseEntity<Iterable<BlogPost>>(blogPostService.getAll(), HttpStatus.OK);
//    }
//
//    @GetMapping("/{tag}")
//    public ResponseEntity<Iterable<BlogPost>> getAllByTag(@PathVariable String tag){
//        return new ResponseEntity<>(blogPostService.getAllByTag(tag), HttpStatus.OK);
//    }
//
////    @GetMapping("/id/{id}")
////    public ResponseEntity<Iterable<BlogPost>> getAllById(@PathVariable Long id){
////        return new ResponseEntity<Iterable<BlogPost>>(blogPostService.getAllById(id), HttpStatus.OK);
////    }
//
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteById(@PathVariable Long id){
//        blogPostService.deleteById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//
//
//    @PutMapping("/id/{id}")
//    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable Long id, @RequestBody BlogPost blogPost){
//        return new ResponseEntity<BlogPost>(blogPostService.updateBlogPost(blogPost, id),HttpStatus.OK);
//    }
//
//
//    @GetMapping("/blog/userName/{userName}")
//    public ResponseEntity<Iterable<BlogPost>> getAllByUserName(@PathVariable String userName){
//        return new ResponseEntity<Iterable<BlogPost>>(blogPostService.findAllByUsername(userName), HttpStatus.OK);
//    }
//}
