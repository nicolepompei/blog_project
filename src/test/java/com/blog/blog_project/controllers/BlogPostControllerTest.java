package com.blog.blog_project.controllers;


import com.blog.blog_project.entities.BlogPost;
import com.blog.blog_project.entities.User;
import com.blog.blog_project.payload.request.PostRequest;
import com.blog.blog_project.payload.request.SignupRequest;
import com.blog.blog_project.payload.response.MessageResponse;
import com.blog.blog_project.payload.response.PostResponse;
import com.blog.blog_project.security.jwt.AuthEntryPointJwt;
import com.blog.blog_project.security.jwt.JwtUtils;
import com.blog.blog_project.security.services.UserDetailsServiceImpl;
import com.blog.blog_project.services.BlogPostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssumptions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Arrays.asList;
import static javax.swing.UIManager.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssumptions.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = BlogPostController.class)
class BlogPostControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    BlogPostService blogPostService;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserDetailsServiceImpl userDetailsServiceImpl;
    @MockBean
    AuthEntryPointJwt authEntryPointJwt;
    @MockBean
    BlogPostController blogPostController;
    @MockBean
    JwtUtils jwtUtils;
//    @Mock
//    List<BlogPost> blogPosts;
//    @BeforeEach
//    public void setUp(){
//        this.blogPosts = new ArrayList<>();
//        this.blogPosts.add(new BlogPost("title","blurb","fulltext","link.img"));
//        this.blogPosts.add(new BlogPost("title2","blurb2","fulltext2","link.img2"));
//    }
    @Test
    public void contextLoads() throws Exception {
        assertThat(blogPostController).isNotNull();
    }

    @Test
    void createBlogPost() throws Exception {

        BlogPost bp = new BlogPost("title","blurb","fulltext","link.img");

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bp)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should list all posts when making a GET request to an endpoint - /posts/")
   public void shouldListAllPost() throws Exception {
        PostResponse pr1 = new PostResponse(1L, "name",  "title", "image.com", "blurb", "full text", null);
        PostResponse pr2 = new PostResponse(2L, "name2",  "2blurb", "2full text", "wwww.2image.com","full text", null);
            System.out.println(pr1);
            System.out.println(Arrays.asList(pr1,pr2));
            Mockito.when(blogPostService.getAllPosts()).thenReturn(Arrays.asList(pr1,pr2));
         System.out.println(blogPostService.getAllPosts());
        mockMvc.perform(MockMvcRequestBuilders.get("/posts").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is(200))
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].id",Matchers.is(1)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].title",Matchers.is("title")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].userName",Matchers.is("name")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].fulltext",Matchers.is("full text")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[0].imageLink",Matchers.is("image.com")))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].id",Matchers.is(2)))
                    .andExpect(MockMvcResultMatchers.jsonPath("$[1].title",Matchers.is("2blurb")));


    }
}