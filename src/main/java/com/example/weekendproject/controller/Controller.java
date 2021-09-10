package com.example.weekendproject.controller;

import com.example.weekendproject.model.Post;
import com.example.weekendproject.model.User;
import com.example.weekendproject.service.PostService;
import com.example.weekendproject.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class Controller {
    ModelAndView modelAndView = new ModelAndView();

    final
    PostService postService;

    final
    UserService userService;

    public Controller(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }


    @GetMapping("/homepage")
    public ModelAndView hello() {
        modelAndView.setViewName("home");
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
        }else if(userService.isUserAlreadyInBase(user)) {
            bindingResult.rejectValue("username", "error.user",
                    "There is already a user registered with the email or username provided");
        } else {
            userService.addUser(user);
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/addPost", method = RequestMethod.GET)
    public ModelAndView showPostForm(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        modelAndView.setViewName("addpost");
        return modelAndView;
    }

    @RequestMapping(value = "/addPost", method = RequestMethod.POST)
    public ModelAndView  addPost(Model model, @Valid Post post, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("addpost");
        } else {
            postService.addPost(post);
            modelAndView.setViewName("redirect:/news");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ModelAndView showNews(Model model,
                                 @RequestParam(defaultValue = "1") int page)
    {
        Page<Post> postList = postService.findAll(page - 1);
        int totalPages = postList.getTotalPages();
        model.addAttribute("currentPage", page);
        model.addAttribute("postList", postList);
        model.addAttribute("totalPages", totalPages);
        modelAndView.setViewName("news");
        return modelAndView;
    }

    @RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
    public ModelAndView getPost(@PathVariable int id, Model model) {
        Optional<Post> post = postService.findPost(id);
        if(post.isEmpty()) {
            Post emptyPost = new Post();
            emptyPost.setNews("There is no post with that id");
            model.addAttribute("post", emptyPost);
        } else {
            model.addAttribute("post", post.get());
        }
        modelAndView.setViewName("lookToOnePost");
        return modelAndView;
    }

    @RequestMapping(value = "/edit/delete/{id}", method = RequestMethod.POST)
    public ModelAndView deletePost(@PathVariable int id, Model model) {
        postService.deletePost(id);
        modelAndView.setViewName("redirect:/news");
        return modelAndView;
    }

    @RequestMapping(value = "/edit/edit/{id}", method = RequestMethod.POST)
    public ModelAndView editPost(@PathVariable int id, Model model) {
        Optional<Post> post = postService.findPost(id);
        if(post.isPresent()){
            model.addAttribute("post", post.get());
            modelAndView.setViewName("addpost");
        } else {
            modelAndView.setViewName("redirect:/news");
        }
        return modelAndView;
    }

}
