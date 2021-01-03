package com.blog.blog_project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USERNAME", unique = true)
    @NotBlank(message = "Username is required")
    @Size(max = 20)
    private String username;

    @Column(name = "PASSWORD")
    @NotBlank(message = "Password is required")
    @Size(max = 120)
    private String password;

    @Column(name = "EMAIL", unique = true)
    @Size(max = 50)
    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    @CreationTimestamp
    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable( name = "BLOG_POSTS",
//                joinColumns = @JoinColumn(name = "USER_ID"),
//                inverseJoinColumns = @JoinColumn(name = "BLOGPOST_ID"))
//    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
//    @Column(name = "BLOGPOSTS")
//    private List<BlogPost> blogPosts = new ArrayList<>();
}
