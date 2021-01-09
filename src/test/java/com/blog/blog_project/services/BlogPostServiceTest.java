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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BlogPostServiceTest {

    @Mock private BlogPostRepository blogPostRepository;
    @Mock private TagRepository tagRepository;
    @Mock private UserRepository userRepository;
    @Mock private AuthService authService;
    @Mock private PostMapper postMapper;

    @Captor
    private ArgumentCaptor<BlogPost> postArgumentCaptor;

    private BlogPostService blogPostService;

    @BeforeEach
    public void setup(){
        blogPostService = new BlogPostService(blogPostRepository, tagRepository, userRepository, authService, postMapper);
    }



    @Test
    @DisplayName("Find a blog post by an id")
    public void shouldFindPostById(){

        BlogPost post = new BlogPost(12L, "Myy blog post", LocalDateTime.now(), LocalDateTime.now(), "blurb", "full text", "wwww.image.com", new HashSet<>(), null);
        PostResponse expectedPostResponse = new PostResponse(12L, "pompy", "Myy blog post", "www.image.com", "blurb", "full text", new HashSet<>());

        Mockito.when(blogPostRepository.findById(12L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(BlogPost.class))).thenReturn(expectedPostResponse);

        PostResponse actualPostResponse = blogPostService.getPost(12L);

        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        Assertions.assertThat(actualPostResponse.getTitle()).isEqualTo(expectedPostResponse.getTitle());

    }

    @Test
    @DisplayName("Find all blog posts by id")
    public void shouldFindAllPostsById(){
        BlogPost post1 = new BlogPost(1L, "Myy blog post", LocalDateTime.now(), LocalDateTime.now(), "blurb", "full text", "wwww.image.com", new HashSet<>(), null);
        BlogPost post2 = new BlogPost(2L, "Myy blog post", LocalDateTime.now(), LocalDateTime.now(), "blurb", "full text", "wwww.image.com", new HashSet<>(), null);

        PostResponse expectedPostResponse1 = new PostResponse(1L, "pompy", "Myy blog post", "www.image.com", "blurb", "full text", new HashSet<>());
        PostResponse expectedPostResponse2 = new PostResponse(2L, "pompy", "Myy blog post", "www.image.com", "blurb", "full text", new HashSet<>());

        List<PostResponse> expectedPostResponses = mock(List.class);

        expectedPostResponses.add(expectedPostResponse1);
        expectedPostResponses.add(expectedPostResponse2);

        Mockito.when(blogPostRepository.findAll()).thenReturn(expectedPostResponses);

    }


    @Test
    @DisplayName("Create and save a blog post")
    public void shouldCreateAndSavePost(){
        User currentUser = new User(1234L, "pompy", "password", "pompy@email.com", LocalDateTime.now(), new ArrayList<>());
        BlogPost post = new BlogPost(12L, "Myy blog post", LocalDateTime.now(), LocalDateTime.now(), "blurb", "full text", "wwww.image.com", new HashSet<>(), null);
        PostRequest postRequest = new PostRequest(12L, "pompy", "Myy blog post", "blurb","full text", "wwww.image.com", new HashSet<>());

        Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);
//        Mockito.when(blogPostRepository.findById(12L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.map(postRequest, currentUser)).thenReturn(post);

        blogPostService.createBlogPost(postRequest);
        //since create post does not return anything, we use verify to verify that a piece of code was executed

        //capture the object that is passed to the method. assert if it matches our expectations or not with with ArgumentCaptor to capture
        //arguments, mockito captures the obj so you have access to it after the method execution
        Mockito.verify(blogPostRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

        Assertions.assertThat(postArgumentCaptor.getValue().getId()).isEqualTo(12L);
        Assertions.assertThat(postArgumentCaptor.getValue().getTitle()).isEqualTo("Myy blog post");
    }

}