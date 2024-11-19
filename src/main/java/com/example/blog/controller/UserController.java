package com.example.blog.controller;

import com.example.blog.entity.User;
import com.example.blog.service.UserService;
import com.example.blog.exception.DuplicateDataException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession session, Model model) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("loginUser", existingUser);
            model.addAttribute("user", existingUser);
            return "redirect:/blogs";
        }
        model.addAttribute("error", messageSource.getMessage("error.login", null, null));
        return "user/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        try {
            userService.save(user);
        } catch (DuplicateDataException e) {
            model.addAttribute("usernameError", e.getMessage());
            return "user/register";
        } catch (Exception e) {
            model.addAttribute("systemError", messageSource.getMessage("error.system", null, null));
            return "user/register";
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
