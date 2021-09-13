package com.example.weekendproject.controller;

import com.example.weekendproject.model.Post;
import com.example.weekendproject.model.Token;
import com.example.weekendproject.model.User;
import com.example.weekendproject.service.PostService;
import com.example.weekendproject.service.TokenService;
import com.example.weekendproject.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class NewsController {

  final PostService postService;
  final UserService userService;
  final TokenService tokenService;

  public NewsController(PostService postService, UserService userService, TokenService tokenService) {
    this.postService = postService;
    this.userService = userService;
    this.tokenService = tokenService;
  }


  @GetMapping("/homepage")
  public String hello() {
    return "home";
  }

  @RequestMapping(value = "/addPost", method = RequestMethod.GET)
  public String showPostForm(Model model) {
    Post post = new Post();
    model.addAttribute("post", post);
    return "addpost";
  }

  @RequestMapping(value = "/addPost", method = RequestMethod.POST)
  public String addPost(Model model, @Valid Post post, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "addpost";
    } else {
      postService.addPost(post);
      return "redirect:/news";
    }
  }

  @RequestMapping(value = "/news", method = RequestMethod.GET)
  public String showNews(Model model,
      @RequestParam(defaultValue = "1") int page) {
    Page<Post> postList = postService.findAll(page - 1);
    int totalPages = postList.getTotalPages();
    model.addAttribute("currentPage", page);
    model.addAttribute("postList", postList);
    model.addAttribute("totalPages", totalPages);
    return "news";
  }

  @RequestMapping(value = "/news/{id}", method = RequestMethod.GET)
  public String getPost(@PathVariable int id, Model model) {
    Optional<Post> post = postService.findPost(id);
    if (post.isEmpty()) {
      Post emptyPost = new Post();
      emptyPost.setNews("There is no post with that id");
      model.addAttribute("post", emptyPost);
    } else {
      model.addAttribute("post", post.get());
    }
    return "lookToOnePost";
  }

  @RequestMapping(value = "/edit/delete/{id}", method = RequestMethod.POST)
  public String deletePost(@PathVariable int id, Model model) {
    postService.deletePost(id);
    return "redirect:/news";
  }

  @RequestMapping(value = "/edit/edit/{id}", method = RequestMethod.POST)
  public String editPost(@PathVariable int id, Model model) {
    Optional<Post> post = postService.findPost(id);
    if (post.isPresent()) {
      model.addAttribute("post", post.get());
      return "addpost";
    } else {
      return "redirect:/news";
    }
  }
}
