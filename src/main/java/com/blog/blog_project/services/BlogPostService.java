package com.blog.blog_project.services;


import com.blog.blog_project.entities.BlogPost;
import com.blog.blog_project.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    BlogPost blogPost;

    public List<BlogPost> findAll(){
        return blogPostRepository.findAll();
    }


    public BlogPost FindAllByTag(String tagName){

        return null;
    }

    public void deleteByID(Long id){
        blogPostRepository.delete(id);
    }


    public BlogPost createBlogPost(BlogPost blogPost){
        return blogPostRepository.save(blogPost);
    }

    public BlogPost updateBlogPost(BlogPost post){
        return blogPostRepository.save(post);


    }
    public BlogPost findAllById(Long userID){
        return null;
    }
    public BlogPost findAllByUsername(String username){
        return null;
    }





}
