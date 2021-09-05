package com.example.weekendproject.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class Controller {
    ModelAndView modelAndView = new ModelAndView();

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
//    @ResponseBody
    public ModelAndView loginPage() {
        modelAndView.setViewName("login");
        return modelAndView;
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ModelAndView loginPageOk() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("hello");
//        return modelAndView;
//    }
}
