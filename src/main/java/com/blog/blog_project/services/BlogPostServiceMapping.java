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
public class BlogPostServiceMapping {

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
        BlogPost post = blogPostRepository.findById(id)
                .orElseThrow(() -> new ZcwBlogPostNotFoundException(id.toString()));
        return postMapper.maptToDto(post);
    }

    /**
     * /////////////////////////////////////  GET ALL POSTS ////////////////////////////////////////
     */
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts(){
        return blogPostRepository.findAll()
                .stream()
                .map(postMapper::maptToDto)
                .collect(Collectors.toList());
    }

    /**
     * /////////////////////////////////////  GET ALL POSTS BY TAG ////////////////////////////////////////
     */
    @Transactional(readOnly = true)
    public List<PostResponse> getAllByTag(String tagName) throws TagNotFoundException {
        Tag tag = tagRepository.findById(tagRepository.findByTagName(tagName).getId())
                .orElseThrow(()-> new TagNotFoundException((tagRepository.findByTagName(tagName).getId().toString())));

        List<BlogPost> posts = blogPostRepository.findByTags_tagName(tagName);
                return posts.stream()
                        .map(postMapper::maptToDto)
                        .collect(Collectors.toList());
    }

    /**
     * /////////////////////////////////////  DELETE A POST BY ID ////////////////////////////////////////
     */
    public void deleteById(Long id){
        blogPostRepository.deleteById(id);
    }



    /**
     * /////////////////////////////////////  CREATE A POST ////////////////////////////////////////
     *  //im thinking we might actually need to add tags to the mapper and do this search in there, if the tags ALREADY exist, then map
     *     //them to the current post. also have an error of tags not found?
     *
     */

    public void createBlogPost(PostRequest postRequest){

        for (Tag t : postRequest.getTags()) {
            if (tagRepository.existsByTagName(t.getTagName())) {
                t.setId(tagRepository.findByTagName(t.getTagName()).getId());
            }
            tagRepository.save(t);
        }


        blogPostRepository.save(postMapper.map(postRequest, authService.getCurrentUser()));
    }

    /**
     * ///////////////////////////////////// UPDATE A POST ////////////////////////////////////////
     *
     *  figure out how to map this to go from a BlogPost Request to a Blog Post Response
     *   when you're updating a post, you've already clicked on it therefore you've gotten it by its id already, so now you're updating the contents
     *   of THAT specific post
     *
     *   COMMENTING OUT FOR NOW AS IT IS NOT PART OF THE MVP
     */

//    public PostResponse updateBlogPost(PostRequest postRequest){
////        BlogPost blogPostToUpdate = blogPostRepository.findById(id)
////                .orElseThrow(() -> new ZcwBlogPostNotFoundException(id.toString()));
////
////        return postMapper.maptToDto(postRequest, blogPostToUpdate);
//
//       // blogPostRepository.findById(id)
//
////                .map(b -> {b.setTitle(postRequest.getTitle());
//////                b.setCreationTimestamp(post.getCreationTimestamp()); Do we need these? Will hibernate handle it itself?
//////                b.setUpdateTimestamp();
////                    b.setBlurb(postRequest.getBlurb());
////                    b.setFulltext(postRequest.getFullText());
////                    b.setImagelink(postRequest.getImageLink());
////                    b.setTags(postRequest.getTags());
//
//        BlogPost blogPost = blogPostRepository.save(postMapper.map(postRequest, authService.getCurrentUser()));
//        return postMapper.maptToDto(blogPost);
//
//    }

    /**
     *     I think we can get rid of getAllById because we can get all by username, which are all unique.
     */
    //    public List<BlogPost> getAllById(Long userID){
//        return blogPostRepository.findByUser_Id(userID);
//    }


    public List<PostResponse> findAllByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return blogPostRepository.findAllByUsername(user.getUsername())
                .stream()
                .map(postMapper::maptToDto)
                .collect(Collectors.toList());
    }
}
