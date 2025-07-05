package com.starbankatm.in.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.starbankatm.in.model.User;
import com.starbankatm.in.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home() {
    return "index";
    }
    
    // GET signup
    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    // POST signup
    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute User user, Model model) {
        boolean created = userService.createAccount(user);
        if (!created) {
            model.addAttribute("error", "Username already exists.");
            return "signup";
        }
        return "index";
    }

    // GET signin
    @GetMapping("/signin")
    public String signinForm() {
        return "signin";
    }

    // POST signin (called via JS when pin is entered)
    @PostMapping("/signin")
    public String signinSubmit(@RequestParam String pin, HttpSession session, Model model) {
    return userService.loginWithPin(pin)
            .map(user -> {
                session.setAttribute("user", user);
                return "redirect:/transaction";
            })
            .orElseGet(() -> {
                model.addAttribute("error", "Incorrect PIN");
                return "signin";
            });
}
}
