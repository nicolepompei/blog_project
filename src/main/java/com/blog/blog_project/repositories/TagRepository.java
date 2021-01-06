package com.blog.blog_project.repositories;

import com.blog.blog_project.entities.Tag;
import com.blog.blog_project.payload.request.PostRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    public boolean existsByTagName(String tagname);
    public Tag findByTagName(String tagname);

   // public Set<Tag> findAllTags(Long blogId);

}