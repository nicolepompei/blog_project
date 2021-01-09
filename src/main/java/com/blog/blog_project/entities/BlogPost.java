package com.blog.blog_project.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "BLOG_POSTS")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BLOGPOST_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @CreationTimestamp
    @Column(name = "TIMESTAMP")
    private LocalDateTime creationTimestamp;

    @UpdateTimestamp
    @Column(name = "UPDATETIMESTAMP")
    private LocalDateTime updateTimestamp;

    @Column(name = "BLURB")
    private String blurb;

    @Column(name = "FULLTEXT")
    private String fulltext;


    @Column(name = "IMAGELINK")
    private String imagelink;


    //This is throwing error "com.fasterxml.jackson.databind.JsonMappingException: Infinite recursion"
    //That is why we must include "JsonManagedReference"
    // https://stackoverflow.com/questions/3325387/infinite-recursion-with-jackson-json-and-hibernate-jpa-issue/39573255#39573255
    @ManyToMany
    @JoinTable(name="BLOGPOST_TAG",
            joinColumns = @JoinColumn(name = "BLOG_ID", referencedColumnName = "BLOGPOST_ID"),
   inverseJoinColumns = @JoinColumn(name = "TAG_ID", referencedColumnName = "id"))
    //@JsonManagedReference
    @JsonIgnoreProperties("blogPosts")
    private Set<Tag> tags;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;


}
