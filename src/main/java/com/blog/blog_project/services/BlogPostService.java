package com.blog.blog_project.services;


import com.blog.blog_project.entities.BlogPost;
import com.blog.blog_project.entities.Tag;
import com.blog.blog_project.exceptions.ZcwBlogPostNotFoundException;
import com.blog.blog_project.payload.request.PostRequest;
import com.blog.blog_project.payload.response.PostResponse;
import com.blog.blog_project.repositories.BlogPostRepository;
import com.blog.blog_project.repositories.TagRepository;
import com.blog.blog_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public List<BlogPost> getAll(){
        return blogPostRepository.findAll();
    }


    public List<BlogPost> getAllByTag(String tagName){
        return blogPostRepository.findByTags_tagName(tagName);
    }

    public void deleteById(Long id){
        blogPostRepository.deleteById(id);
    }

    //we can't NOT save the tag (or can we?) and if we don't do this we'll have duplicate tags.
    //Here is a workaround.
    public BlogPost createBlogPost(BlogPost blogPost){
        for (Tag t : blogPost.getTags()) {
            if (tagRepository.existsByTagName(t.getTagName())) {
                t.setId(tagRepository.findByTagName(t.getTagName()).getId());
            }
            tagRepository.save(t);
        }
        blogPost.setUsername(authService.getCurrentUser().getUsername());
//        blogPost.setUsername((blogPost.getUsername()));
        return blogPostRepository.save(blogPost);
    }

  //re-qork these methods to take in a blog post resquest and return a blog post response


    //figure out how to map this to go from a BlogPost Request to a Blog Post Response
    public BlogPost updateBlogPost(BlogPost post, Long id){
        blogPostRepository.findById(id)
                .map(b -> {b.setTitle(post.getTitle());
//                b.setCreationTimestamp(post.getCreationTimestamp()); Do we need these? Will hibernate handle it itself?
//                b.setUpdateTimestamp();
                b.setBlurb(post.getBlurb());
                b.setFulltext(post.getFulltext());
                b.setImagelink(post.getImagelink());
                b.setTags(post.getTags());
                return blogPostRepository.save(b);
                });
        BlogPost postResponse = blogPostRepository.findById(id)
                .orElseThrow(() -> new ZcwBlogPostNotFoundException(id.toString()));
       // return blogPostRepository.findById(id).orElseThrow(RuntimeException::new);

        return postResponse;
    }

//    public List<BlogPost> getAllById(Long userID){
//        return blogPostRepository.findByUser_Id(userID);
//    }
    //TODO In case we want to allow users to search by username
//    public List<BlogPost> findAllByUsername(String username){
//        return blogPostRepository.findAllByUsername(username);
//    }

}
