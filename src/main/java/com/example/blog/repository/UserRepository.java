package com.example.blog.repository;

import com.example.blog.entity.User;

public interface UserRepository {
    User findByUsername(String username);

    int countByUsername(String username);

    void save(User user);
}
