package com.blog.blog_project.services;

import com.blog.blog_project.entities.BlogPost;
import com.blog.blog_project.entities.User;
import com.blog.blog_project.mapper.PostMapper;
import com.blog.blog_project.payload.request.PostRequest;
import com.blog.blog_project.payload.response.PostResponse;
import com.blog.blog_project.repositories.BlogPostRepository;
import com.blog.blog_project.repositories.TagRepository;
import com.blog.blog_project.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {

    @Mock private BlogPostRepository blogPostRepository;
    @Mock private TagRepository tagRepository;
    @Mock private UserRepository userRepository;
    @Mock private AuthService authService;
    @Mock private PostMapper postMapper;

    private BlogPostService blogPostService;

    @BeforeEach
    public void setup(){
        blogPostService = new BlogPostService(blogPostRepository, tagRepository, userRepository, authService, postMapper);
    }



    @Test
    @DisplayName("Find a blog post by an id")
    void shouldFindPostById(){

        User pompy = new User(1L, "pompy","password", "pompy@email.com", LocalDateTime.now(), new ArrayList<>());

        BlogPost post = new BlogPost(12L, "Myy blog post", LocalDateTime.now(), LocalDateTime.now(), "blurb", "full text", "wwww.image.com", new HashSet<>(), pompy);
        PostResponse expectedPostResponse = new PostResponse(12L, "pompy", "Myy blog post", "www.image.com", "blurb", "full text", new HashSet<>());

        Mockito.when(blogPostRepository.findById(12L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(BlogPost.class))).thenReturn(expectedPostResponse);

        PostResponse actualPostResponse = blogPostService.getPost(12L);

        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        Assertions.assertThat(actualPostResponse.getTitle()).isEqualTo(expectedPostResponse.getTitle());

    }
}