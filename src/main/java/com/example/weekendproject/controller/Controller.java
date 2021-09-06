package com.example.weekendproject.controller;

import com.example.weekendproject.model.User;
import com.example.weekendproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;

@RestController
public class Controller {
    ModelAndView modelAndView = new ModelAndView();

    @Autowired
    UserService userService;


    @GetMapping("/home")
    public ModelAndView homepage() {
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/hello")
    public ModelAndView hello(Model model) {
        String testMessage = "testTEST";
        model.addAttribute("userMessage", testMessage);
        modelAndView.setViewName("hello");
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView loginPage() {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView signUpGet(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView signUpPost(User user) {
        userService.addUser(user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

}
