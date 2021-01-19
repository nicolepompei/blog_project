package com.blog.blog_project.repositories;

import com.blog.blog_project.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {



    List<BlogPost> findByTags_tagName(String tagName);

    List<BlogPost> findAllByUsername(String Username);
}
