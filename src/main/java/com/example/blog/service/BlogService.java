package com.example.blog.service;

<<<<<<< Updated upstream
import com.example.blog.entity.Blog;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.exception.DuplicateDataException;
import com.example.blog.exception.DatabaseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.MessageSource;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MessageSource messageSource;

=======
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.blog.entity.Blog;
import com.example.blog.form.BlogForm;
import com.example.blog.repository.BlogRepository;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    // ブログの新規作成または更新
    public void saveBlog(BlogForm blogForm) {
        Blog blog;
        if (blogForm.getId() != null) {
            blog = blogRepository.findById(blogForm.getId());
        if (blog == null) {
            throw new IllegalArgumentException("ブログが見つかりません: ID = " + blogForm.getId());
        }
        } else {
            blog = new Blog();
        }
        
        blog.setTitle(blogForm.getTitle());
        blog.setContent(blogForm.getContent());

        blogRepository.save(blog);
    }

    // すべてのブログを取得
>>>>>>> Stashed changes
    public List<Blog> findAll() {
        return blogRepository.findAll();
    }

<<<<<<< Updated upstream
    public Blog findById(Integer id) {
        return blogRepository.findById(id);
    }

    @Transactional
    public void createBlog(Blog blog) {
        if (isTitleDuplicate(blog.getTitle())) {
            throw new DuplicateDataException(
                    messageSource.getMessage("error.blog.title.duplicate", null, null));
        }

        try {
            blogRepository.save(blog);
        } catch (Exception e) {
            throw new DatabaseException(messageSource.getMessage("error.database", null, null),
                    e);
        }
    }

    @Transactional
    public void updateBlog(Integer id, Blog blog) {
        if (isTitleDuplicateForUpdate(blog.getTitle(), id)) {
            throw new DuplicateDataException(
                    messageSource.getMessage("error.blog.title.duplicate", null, null));
        }

        try {
            blog.setId(id);
            blogRepository.update(blog);
        } catch (Exception e) {
            throw new DatabaseException(
                    messageSource.getMessage("error.database", null, null), e);
        }
    }

    @Transactional
    public void delete(Integer id) {
        commentRepository.deleteByBlogId(id);
        blogRepository.delete(id);
    }

    public boolean isTitleDuplicate(String title) {
        return blogRepository.countByTitle(title) > 0;
    }

    public boolean isTitleDuplicateForUpdate(String title, Integer id) {
        return blogRepository.countByTitleAndNotId(title, id) > 0;
=======
    // ID でブログを取得
    public Blog findById(int id) {
        Blog blog = blogRepository.findById(id);
        if (blog == null) {
            throw new IllegalArgumentException("ブログが見つかりません: ID = " + id);
        }
        return blog;
    }

    // 詳細表示のためのメソッド
    public Blog detail(Integer id) {
        return findById(id);
    }

    // ID でブログを削除
    public void deleteById(int id) {
        Blog blog = findById(id);
        if (blog == null) {
            throw new IllegalArgumentException("削除するブログが見つかりません: ID = " + id);
        }
        blogRepository.deleteById(id); // 削除メソッドを呼び出す
    }

    // 新規ブログを作成するメソッド
    public void create(BlogForm blogForm) {
        Blog blog = new Blog();
        blog.setTitle(blogForm.getTitle());
        blog.setContent(blogForm.getContent());

        blogRepository.save(blog);  // 新規作成の際には新しいエンティティを保存
    }

    // 既存ブログを保存するメソッド（更新）
    public void save(Blog existingBlog) {
        blogRepository.save(existingBlog);  // 既存のブログエンティティを保存
>>>>>>> Stashed changes
    }
}
