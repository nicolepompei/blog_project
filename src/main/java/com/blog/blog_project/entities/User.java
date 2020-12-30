package com.blog.blog_project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;
    @Column(name = "USERNAME", unique = true)
//    @NotBlank(message = "Username is required")
    private String username;

    @Column(name = "PASSWORD")
//    @NotBlank(message = "Password is required")
    private String password;

    @Column(name = "EMAIL", unique = true)
//    @Email
//    @NotEmpty(message = "Email is required")
    private String email;

    @CreationTimestamp
    @Column(name = "TIMESTAMP")
    private LocalDateTime timestamp;

    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @Column(name = "BLOGPOSTS")
    private List<BlogPost> blogPosts = new ArrayList<>();
}
