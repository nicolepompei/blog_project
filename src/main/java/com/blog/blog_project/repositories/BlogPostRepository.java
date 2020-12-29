package com.blog.blog_project.repositories;

import com.blog.blog_project.entities.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    public List<BlogPost> findByUser_Id(Long userId);
    //How is this going to work?
    public List<BlogPost> findByTags_tagName(String tagName);

    public List<BlogPost> findAllByUsername(String Username);
}
