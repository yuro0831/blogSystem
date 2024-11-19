package com.example.blog.service;

import com.example.blog.entity.Comment;
import com.example.blog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> findByBlogId(Integer blogId) {
        return commentRepository.findByBlogId(blogId);
    }

    public Comment findById(Integer id) {
        return commentRepository.findById(id);
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public void update(Comment comment) {
        commentRepository.update(comment);
    }

    public void delete(Integer id) {
        commentRepository.delete(id);
    }
}
