package com.blog.blog_project.entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinition;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "TAG_NAME")
    private String tagName;
    @ManyToMany(mappedBy = "BLOGPOST_ID")
    private Set<BlogPost> blogPosts = new HashSet<>();
}