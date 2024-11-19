package com.example.blog.repository;

import com.example.blog.entity.Comment;
import java.util.List;

public interface CommentRepository {
    List<Comment> findByBlogId(Integer blogId);

    Comment findById(Integer id);

    void save(Comment comment);

    void update(Comment comment);

    void delete(Integer id);

    void deleteByBlogId(Integer blogId);
}
