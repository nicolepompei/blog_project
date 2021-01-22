package com.blog.blog_project.services;

import com.blog.blog_project.entities.BlogPost;
import com.blog.blog_project.entities.Tag;
import com.blog.blog_project.entities.User;
import com.blog.blog_project.exceptions.TagNotFoundException;
import com.blog.blog_project.exceptions.ZcwBlogPostNotFoundException;
import com.blog.blog_project.mapper.PostMapper;
import com.blog.blog_project.payload.request.PostRequest;
import com.blog.blog_project.payload.response.PostResponse;
import com.blog.blog_project.repositories.BlogPostRepository;
import com.blog.blog_project.repositories.TagRepository;
import com.blog.blog_project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private PostMapper postMapper;


    /**
     * /////////////////////////////////////  GET A POST BY ID ////////////////////////////////////////
     */
    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) throws ZcwBlogPostNotFoundException{
        log.info("getPost called");
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ZcwBlogPostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    /**
     * /////////////////////////////////////  GET ALL POSTS ////////////////////////////////////////
     */
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        log.info("getAllPosts called");
        return blogPostRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * /////////////////////////////////////  GET ALL POSTS BY TAG ////////////////////////////////////////
     */
    @Transactional(readOnly = true)
    public List<PostResponse> getAllByTag(String tagName) throws TagNotFoundException {
        log.info("getAllByTag called");
        Tag tag = tagRepository.findById(tagRepository.findByTagName(tagName).getId())
                .orElseThrow(()-> new TagNotFoundException((tagRepository.findByTagName(tagName).getId().toString())));

        List<BlogPost> posts = blogPostRepository.findByTags_tagName(tagName);
                return posts.stream()
                        .map(postMapper::mapToDto)
                        .collect(Collectors.toList());
    }



    /**
     * /////////////////////////////////////  CREATE A POST ////////////////////////////////////////
     *  //im thinking we might actually need to add tags to the mapper and do this search in there, if the tags ALREADY exist, then map
     *     //them to the current post. also have an error of tags not found?
     *
     */

    public void createBlogPost(PostRequest postRequest){
        log.info("createBlogPost called");
        if (postRequest.getTags() != null) {

            for (Tag t : postRequest.getTags()) {
                if (tagRepository.existsByTagName(t.getTagName())) {
                    t.setId(tagRepository.findByTagName(t.getTagName()).getId());
                }
                tagRepository.save(t);
            }
        }
        blogPostRepository.save(postMapper.map(postRequest, authService.getCurrentUser()));
    }


    /**
     * ////////////////////////////////// FIND ALL BY USERNAME ////////////////////////////////////////////////////////
     * @param currentUserUsername
     * @return
     */

    public List<PostResponse> findAllByUsername(String currentUserUsername){
        log.info("findAllByUsername called");
        User user = userRepository.findByUsername(currentUserUsername)
                .orElseThrow(() -> new UsernameNotFoundException(currentUserUsername));
        return blogPostRepository.findAllByUsername(user.getUsername())
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());

        //user.getUsername()
//        authService.getCurrentUser().getUsername()
    }
}
