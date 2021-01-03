package com.blog.blog_project.controllers;

import com.blog.blog_project.entities.BlogPost;
import com.blog.blog_project.exceptions.TagNotFoundException;
import com.blog.blog_project.payload.request.PostRequest;
import com.blog.blog_project.payload.response.PostResponse;
import com.blog.blog_project.services.BlogPostService;
import com.blog.blog_project.services.BlogPostServiceMapping;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RequestMapping("/posts")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class BlogPostControllerMapping {

    @Autowired
    private BlogPostServiceMapping blogPostService;

//    public BlogPostControllerMapping(BlogPostService blogPostService){
//        this.blogPostService = blogPostService;
//    }

    /**
     * ///////////////////////////////////// CREATE A BLOG POST ///////////////////////////////////////
     * @param
     * @return
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createBlogPost(@RequestBody PostRequest postRequest){
        blogPostService.createBlogPost(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    /**
     * ///////////////////////////////////// GET A BLOG POST ///////////////////////////////////////
     * @param
     * @return
     */

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return status(HttpStatus.OK).body(blogPostService.getPost(id));
    }

    /**
     * ///////////////////////////////////// GET ALL BLOG POSTS ///////////////////////////////////////
     * @param
     * @return
     */
    @GetMapping
    public ResponseEntity<Iterable<PostResponse>> getAllPosts(){
        return status(HttpStatus.OK).body(blogPostService.getAllPosts());
    }

    /**
     * ///////////////////////////////////// GET ALL BLOG POSTS BY TAG ///////////////////////////////////////
     * @param
     * @return
     */
    @GetMapping("/{tag}")
    public ResponseEntity<Iterable<PostResponse>> getAllByTag(@PathVariable String tag) throws TagNotFoundException {
        return status(HttpStatus.OK).body(blogPostService.getAllByTag(tag));
    }

//    @GetMapping("/id/{id}")
//    public ResponseEntity<Iterable<BlogPost>> getAllById(@PathVariable Long id){
//        return new ResponseEntity<Iterable<BlogPost>>(blogPostService.getAllById(id), HttpStatus.OK);
//    }

    /**
     * ///////////////////////////////////// DELETE POST BY ID ///////////////////////////////////////
     * @param
     * @return
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        blogPostService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * ///////////////////////////////////// UPDATE POSTS BY USERNAME ///////////////////////////////////////
     * Commenting this out for now as it is not apart of the MVP.
     */
//    @PutMapping("/id/{id}")
//    public ResponseEntity<PostResponse> updateBlogPost(@PathVariable Long id, @RequestBody PostRequest postRequest){
//        return status(HttpStatus.OK).body(blogPostService.updateBlogPost(postRequest, id));
//    }

    /**
     * ///////////////////////////////////// GET ALL BLOG POSTS BY USERNAME ///////////////////////////////////////
     * @param
     * @return
     */

    @GetMapping("/blog/userName/{userName}")
    public ResponseEntity<Iterable<PostResponse>> getAllByUserName(@PathVariable String userName){
        return status(HttpStatus.OK).body(blogPostService.findAllByUsername(userName));
    }
}
