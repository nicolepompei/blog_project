package com.blog.blog_project.services;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class BlogPostServiceConfiguration {
    @Bean
    @Primary
    public BlogPostService blogPostService(){
        return Mockito.mock(BlogPostService.class);
    }
}
