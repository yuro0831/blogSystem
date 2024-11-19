package com.example.blog.repository;

<<<<<<< Updated upstream
import com.example.blog.entity.Blog;
import java.util.List;

public interface BlogRepository {
    List<Blog> findAll();

    Blog findById(Integer id);

    void save(Blog blog);

    void update(Blog blog);

    void delete(Integer id);

    Integer countByTitle(String title);

    Integer countByTitleAndNotId(String title, Integer id);
=======
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.blog.entity.Blog;

@Repository
public interface BlogRepository {
    public List<Blog> findAll();
    public Blog findById(int id);
    public void save(Blog blog);
    public void update(Blog blog);
    public void deleteById(int id);
>>>>>>> Stashed changes
}
