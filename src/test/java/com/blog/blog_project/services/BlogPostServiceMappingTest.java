package com.blog.blog_project.services;

import com.blog.blog_project.entities.BlogPost;
import com.blog.blog_project.mapper.PostMapper;
import com.blog.blog_project.payload.request.PostRequest;
import com.blog.blog_project.payload.response.PostResponse;
import com.blog.blog_project.repositories.BlogPostRepository;
import com.blog.blog_project.repositories.TagRepository;
import com.blog.blog_project.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

class BlogPostServiceMappingTest {



    private BlogPostRepository blogPostRepository = Mockito.mock(BlogPostRepository.class);
    private TagRepository tagRepository = Mockito.mock(TagRepository.class);
    private UserRepository userRepository = Mockito.mock(UserRepository.class);
    private AuthService authService = Mockito.mock(AuthService.class);
    private PostMapper postMapper = Mockito.mock(PostMapper.class);



    @Test
    @DisplayName("Find a blog post by an id")
    void shouldFindPostById(){
        BlogPostServiceMapping blogPostServiceMapping = new BlogPostServiceMapping(blogPostRepository, tagRepository, userRepository, authService, postMapper);

        BlogPost post = new BlogPost(12L, "Myy blog post", LocalDateTime.now(), LocalDateTime.now(), "blurb", "full text", "pompy", "wwww.image.com", new HashSet<>(), null);
        PostResponse expectedPostResponse = new PostResponse(12L, "pompy", "Myy blog post", "www.image.com", "here is a blurb", "here is the full text ayyayay", new HashSet<>());

        Mockito.when(blogPostRepository.findById(1234L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(BlogPost.class))).thenReturn(expectedPostResponse);

        PostResponse actualPostResponse = blogPostServiceMapping.getPost(12L);

        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        Assertions.assertThat(actualPostResponse.getTitle()).isEqualTo(expectedPostResponse.getTitle());




    }
}