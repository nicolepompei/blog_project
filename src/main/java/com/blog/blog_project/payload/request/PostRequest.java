package com.blog.blog_project.payload.request;

import com.blog.blog_project.entities.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private Long postId;
    private String username;
    private String title;
    private String blurb;
    private String fulltext;
    private String imagelink;
    private Set<Tag> tags;

}
