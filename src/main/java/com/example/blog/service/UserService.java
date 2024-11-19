package com.example.blog.service;

import com.example.blog.entity.User;
import com.example.blog.repository.UserRepository;
import com.example.blog.exception.DuplicateDataException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.context.MessageSource;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSource messageSource;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void save(User user) {
        if (userRepository.countByUsername(user.getUsername()) > 0) {
            throw new DuplicateDataException(messageSource.getMessage("error.username.duplicate", null, null));
        }

        userRepository.save(user);
    }
}
