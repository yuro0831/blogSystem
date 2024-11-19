package com.example.blog.repository;

import com.example.blog.entity.Category;
import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
}
