package com.blog.blog_project.controllers;

import com.blog.blog_project.entities.User;
import com.blog.blog_project.exceptions.TagNotFoundException;
import com.blog.blog_project.payload.request.PostRequest;
import com.blog.blog_project.payload.response.PostResponse;
//import com.blog.blog_project.services.BlogPostService;
import com.blog.blog_project.services.BlogPostService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RequestMapping("/posts")
@RestController
@AllArgsConstructor
@Slf4j
// @CrossOrigin(origins = "http://localhost:4200")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    /**
     * ///////////////////////////////////// CREATE A BLOG POST ///////////////////////////////////////
     * @param
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createBlogPost(@RequestBody PostRequest postRequest){
        log.info("Create blog post controller executing");
        blogPostService.createBlogPost(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * ///////////////////////////////////// GET A BLOG POST ///////////////////////////////////////
     * @param
     * @return
     */

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        log.info("get post by ID controller called");
        return status(HttpStatus.OK).body(blogPostService.getPost(id));
    }

    /**
     * ///////////////////////////////////// GET ALL BLOG POSTS ///////////////////////////////////////
     * @param
     * @return
     */
    @GetMapping
    public ResponseEntity<Iterable<PostResponse>> getAllPosts(){
        log.info("get all posts controller executed: getting all posts");
        return status(HttpStatus.OK).body(blogPostService.getAllPosts());
    }

    /**
     * ///////////////////////////////////// GET ALL BLOG POSTS BY TAG ///////////////////////////////////////
     * @param
     * @return
     */
    @GetMapping("/tags/{tag}")
    public ResponseEntity<Iterable<PostResponse>> getAllByTag(@PathVariable String tag) throws TagNotFoundException {
        log.info("get all posts by tag executed: getting all posts by tag");
        return status(HttpStatus.OK).body(blogPostService.getAllByTag(tag));
    }



    /**
     * ///////////////////////////////////// GET ALL BLOG POSTS BY USERNAME ///////////////////////////////////////
     * @param
     * @return
     */

    @GetMapping("/blog/userName/{userName}")
    public ResponseEntity<Iterable<PostResponse>> getAllByuserName(@PathVariable String userName){
        log.info("get all posts by username exectued: getting all posts");
        return status(HttpStatus.OK).body(blogPostService.findAllByUsername(userName));
    }
}
