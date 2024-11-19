package com.example.blog.controller;

<<<<<<< Updated upstream
import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.service.BlogService;
import com.example.blog.service.CategoryService;
import com.example.blog.service.CommentService;
import com.example.blog.exception.DatabaseException;
import com.example.blog.exception.DuplicateDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/blogs")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public String list(Model model) {
=======
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.blog.entity.Blog;
import com.example.blog.form.BlogForm;
import com.example.blog.service.BlogService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/blogs")
public class BlogController {

    private final BlogService blogService;

    // 一覧表示
    @GetMapping
    public String listBlogs(Model model) {
>>>>>>> Stashed changes
        List<Blog> blogs = blogService.findAll();
        model.addAttribute("blogs", blogs);
        return "blog/list";
    }

<<<<<<< Updated upstream
    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        Blog blog = blogService.findById(id);
        List<Comment> comments = commentService.findByBlogId(id);
        blog.setComments(comments);
        model.addAttribute("blog", blog);

        if (!model.containsAttribute("comment")) {
            model.addAttribute("comment", new Comment());
        }

        return "blog/detail";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        if (!model.containsAttribute("blog")) {
            model.addAttribute("blog", new Blog());
        }

        model.addAttribute("categories", categoryService.findAll());
        return "blog/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute Blog blog, BindingResult bindingResult, HttpSession session,
            Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.blog",
                    bindingResult);
            redirectAttributes.addFlashAttribute("blog", blog);
            return "redirect:/blogs/new";
        }

        User loginUser = (User) session.getAttribute("loginUser");
        blog.setUser(loginUser);

        try {
            blogService.createBlog(blog);
        } catch (DuplicateDataException e) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.blog",
                    bindingResult);
            redirectAttributes.addFlashAttribute("blog", blog);
            redirectAttributes.addFlashAttribute("titleError", e.getMessage());
            return "redirect:/blogs/new";
        } catch (DatabaseException e) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.blog",
                    bindingResult);
            redirectAttributes.addFlashAttribute("blog", blog);
            redirectAttributes.addFlashAttribute("databaseError", e.getMessage());
            return "redirect:/blogs/new";
        }

        return "redirect:/blogs";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        Blog blog = blogService.findById(id);
        List<Category> categories = categoryService.findAll();
        model.addAttribute("blog", blog);
        model.addAttribute("categories", categories);
        return "blog/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute Blog blog, BindingResult bindingResult,
            HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.blog", bindingResult);
            redirectAttributes.addFlashAttribute("blog", blog);
            return "redirect:/blogs/" + id + "/edit";
        }

        User loginUser = (User) session.getAttribute("loginUser");
        blog.setUser(loginUser);

        try {
            blogService.updateBlog(id, blog);
        } catch (DuplicateDataException e) {
            redirectAttributes.addFlashAttribute("titleError", e.getMessage());
            redirectAttributes.addFlashAttribute("blog", blog);
            return "redirect:/blogs/" + id + "/edit";
        } catch (DatabaseException e) {
            redirectAttributes.addFlashAttribute("databaseError", e.getMessage());
            redirectAttributes.addFlashAttribute("blog", blog);
            return "redirect:/blogs/" + id + "/edit";
        }

        return "redirect:/blogs/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        blogService.delete(id);
=======
    // 詳細表示
    @GetMapping("/{id}")
    public String detailBlog(@PathVariable Integer id, Model model) {
        Blog blog = blogService.detail(id);
        if (blog != null) {
            model.addAttribute("blog", blog);
            return "blog/detail";
        } else {
            model.addAttribute("errorMessage", "ブログが見つかりません: ID = " + id);
            return "error";
        }
    }

    // 作成フォームの表示
    @GetMapping("/new")
    public String newBlogForm(Model model) {
        model.addAttribute("blogForm", new BlogForm());
        return "blog/form";
    }

    // 作成処理
    @PostMapping
    public String createBlog(@ModelAttribute @Valid BlogForm blogForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // 入力にエラーがある場合は、再度フォーム画面を表示する
            model.addAttribute("blogForm", blogForm);
            return "blog/form"; // バリデーションエラー時にフォームを再表示
        }
        blogService.saveBlog(blogForm);
        return "redirect:/blogs";
    }

    // 更新フォームの表示
    @GetMapping("/{id}/edit")
    public String editBlogForm(@PathVariable Integer id, Model model) {
        try {
            Blog blog = blogService.findById(id);
            BlogForm blogForm = new BlogForm();
            blogForm.setId(blog.getId());
            blogForm.setTitle(blog.getTitle());
            blogForm.setContent(blog.getContent());
            model.addAttribute("blogForm", blogForm);
            return "blog/form";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    // 更新処理
    @PostMapping("/{id}")
    public String updateBlog(@PathVariable Integer id, @ModelAttribute @Valid BlogForm blogForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // バリデーションエラー時にフォームを再表示する
            model.addAttribute("blogForm", blogForm);
            return "blog/form";
        }

        blogForm.setId(id);
        try {
            blogService.saveBlog(blogForm);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "ブログが見つかりません: ID = " + id);
            return "error";
        }
        return "redirect:/blogs";
    }

    // 削除処理
    @PostMapping("/{id}/delete")
    public String deleteBlog(@PathVariable Integer id, Model model) {
        try {
            blogService.deleteById(id);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", "削除するブログが見つかりません: ID = " + id);
            return "error";
        }
>>>>>>> Stashed changes
        return "redirect:/blogs";
    }
}
