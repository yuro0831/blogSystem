package com.example.blog.controller;

import com.example.blog.entity.Blog;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/blogs/{blogId}/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public String list(@PathVariable Integer blogId, Model model) {
        List<Comment> comments = commentService.findByBlogId(blogId);
        model.addAttribute("comments", comments);
        Blog blog = new Blog();
        blog.setId(blogId);
        model.addAttribute("blog", blog);
        return "comment/list";
    }

    @GetMapping("/new")
    public String createForm(@PathVariable Integer blogId, Model model) {
        Comment comment = new Comment();
        model.addAttribute("comment", comment);
        return "comment/form";
    }

    @PostMapping("/create")
    public String create(@PathVariable Integer blogId, @Valid @ModelAttribute Comment comment,
            BindingResult bindingResult, HttpSession session, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.comment", bindingResult);
            redirectAttributes.addFlashAttribute("comment", comment);
            return "redirect:/blogs/" + blogId;
        }

        User loginUser = (User) session.getAttribute("loginUser");
        comment.setUser(loginUser);
        commentService.save(comment);
        return "redirect:/blogs/" + blogId;
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer blogId, @PathVariable Integer id, Model model) {
        Comment comment = commentService.findById(id);
        model.addAttribute("comment", comment);
        return "comment/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Integer blogId, @PathVariable Integer id, @ModelAttribute Comment comment) {
        comment.setId(id);
        commentService.update(comment);
        return "redirect:/blogs/" + blogId;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer blogId, @PathVariable Integer id) {
        commentService.delete(id);
        return "redirect:/blogs/" + blogId;
    }
}
