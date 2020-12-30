package com.blog.blog_project.services;


import com.blog.blog_project.entities.BlogPost;
import com.blog.blog_project.entities.User;
import com.blog.blog_project.repositories.BlogPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.time.Instant.now;

@Service
@Slf4j
public class BlogPostService {
    @Autowired
    private BlogPostRepository blogPostRepository;

    public List<BlogPost> getAll(){
        return blogPostRepository.findAll();
    }


    public List<BlogPost> getAllByTag(String tagName){
        return blogPostRepository.findByTags_tagName(tagName);
    }

    public void deleteById(Long id){
        blogPostRepository.deleteById(id);
    }

    public BlogPost createBlogPost(BlogPost blogPost){
        BlogPost newBlogPost = new BlogPost();
        newBlogPost.setTitle(blogPost.getTitle());
        newBlogPost.setBlurb(blogPost.getBlurb());
        newBlogPost.setImagelink(blogPost.getImagelink());
        newBlogPost.setFulltext(blogPost.getFulltext());
        newBlogPost.setTimestamp(now());
        newBlogPost.setUsername(blogPost.getUsername());
        //newBlogPost.getUser().getId();

        blogPostRepository.save(newBlogPost);
        log.info("Blog created successfully, sending email");
        return newBlogPost;
    }

    public BlogPost updateBlogPost(BlogPost post, Long id){
        blogPostRepository.findById(id)
                .map(b -> {b.setTitle(post.getTitle());
                b.setTimestamp(now());
                b.setBlurb(post.getBlurb());
                b.setFulltext(post.getFulltext());
                b.setImagelink(post.getImagelink());
                return blogPostRepository.save(b);
                });
        return blogPostRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public List<BlogPost> getAllById(Long userID){
        return blogPostRepository.findByUser_Id(userID);
    }
    //TODO In case we want to allow users to search by username
//    public List<BlogPost> findAllByUsername(String username){
//        return blogPostRepository.findAllByUsername(username);
//    }

}
