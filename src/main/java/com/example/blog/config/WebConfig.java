package com.example.blog.config;

import com.example.blog.filter.SessionCheckFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Autowired
    private SessionCheckFilter sessionCheckFilter;

    @Bean
    public FilterRegistrationBean<SessionCheckFilter> sessionCheckFilterRegistration() {
        FilterRegistrationBean<SessionCheckFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(sessionCheckFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }
}