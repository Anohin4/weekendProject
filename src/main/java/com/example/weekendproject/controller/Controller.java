package com.example.weekendproject.controller;

import com.example.weekendproject.model.Role;
import com.example.weekendproject.model.User;
import com.example.weekendproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@RestController
public class Controller {
    ModelAndView modelAndView = new ModelAndView();

    @Autowired
    UserService userService;


    @GetMapping("/homepage")
    public ModelAndView hello() {
        if(userService.isAnon()) {
            modelAndView.setViewName("home");
        } else if(userService.isCurrentUserAdmin()) {
            modelAndView.setViewName("adminHome");
        }else {
            modelAndView.setViewName("userHome");
        }
        return modelAndView;
    }

    @RequestMapping("/login")
    public ModelAndView loginPage() {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView showSignUpForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView signUp(@Valid  User user, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        }
        if(userService.isUserAlreadyInBase(user)) {
            bindingResult.rejectValue("username", "error.user",
                    "There is already a user registered with the email or username provided");
        } else {
            userService.addUser(user);
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }


}
