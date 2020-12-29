package com.blog.blog_project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

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
    @NotBlank(message = "Username is required")
    private String username;

    @Column(name = "PASSWORD")
    @NotBlank(message = "Password is required")
    private String password;

    @Column(name = "EMAIL", unique = true)
    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    @Column(name = "TIMESTAMP")
    private Instant timestamp;

    @OneToMany(cascade = CascadeType.ALL, fetch = LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @Column(name = "BLOGPOSTS")
    private List<BlogPost> blogPosts = new ArrayList<>();
}
