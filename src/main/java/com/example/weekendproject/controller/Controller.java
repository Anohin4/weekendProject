package com.example.weekendproject.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/home")
    public String homepage() {
        return "home";
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/login-error")
    public String loginFail(Model model) {
        model.addAttribute("loginError",true);
        return "login-error";
    }

    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }
}
