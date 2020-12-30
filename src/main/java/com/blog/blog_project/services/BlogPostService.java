package com.blog.blog_project.services;


import com.blog.blog_project.entities.BlogPost;
import com.blog.blog_project.entities.Tag;
import com.blog.blog_project.entities.User;
import com.blog.blog_project.repositories.BlogPostRepository;
import com.blog.blog_project.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private TagRepository tagRepository;

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
        for (Tag t : blogPost.getTags()){
            tagRepository.save(t);
        }
        return blogPostRepository.save(blogPost);
    }

    public BlogPost updateBlogPost(BlogPost post, Long id){
        blogPostRepository.findById(id)
                .map(b -> {b.setTitle(post.getTitle());
                b.setTimestamp(post.getTimestamp());
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
