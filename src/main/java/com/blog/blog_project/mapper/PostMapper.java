//package com.blog.blog_project.mapper;
//
//import com.blog.blog_project.entities.BlogPost;
//import com.blog.blog_project.entities.User;
//import com.blog.blog_project.payload.request.PostRequest;
//import com.blog.blog_project.payload.response.PostResponse;
//import com.blog.blog_project.services.AuthService;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Mapper(componentModel = "spring")
//public interface  PostMapper {
//
//
//    @Mapping(target = "creationTimestamp", expression = "java(java.util.LocalDateTime())")
//    @Mapping(target = "description", source = "postRequest.description")
//    @Mapping(target = "subreddit", source = "subreddit")
//    @Mapping(target = "user", source = "user")
//    BlogPost map(PostRequest postRequest, User user);
//
//    @Mapping(target = "id", source = "postId")
//    @Mapping(target = "subredditName", source = "subreddit.name")
//    @Mapping(target = "userName", source = "user.username")
//    @Mapping(target = "duration", expression = "java(getDuration(post))")
//    PostResponse mapToDto(BlogPost post);
//}
