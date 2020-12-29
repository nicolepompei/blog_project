package com.blog.blog_project.entities;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tagName;
    @ManyToMany(mappedBy = "blogPost")
    private Set<BlogPost> blogs = new HashSet<>();
}