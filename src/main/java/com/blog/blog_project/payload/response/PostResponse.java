package com.blog.blog_project.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private String userName;
    private String title;
    private String imageLink;
    private String blurb;
    private String fullText;


}
