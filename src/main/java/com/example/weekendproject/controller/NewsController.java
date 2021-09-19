package com.example.weekendproject.controller;

import com.example.weekendproject.model.Image;
import com.example.weekendproject.model.Post;
import com.example.weekendproject.service.ImageService;
import com.example.weekendproject.service.PostService;
import com.example.weekendproject.service.TokenService;
import com.example.weekendproject.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NewsController {

  final PostService postService;
  final UserService userService;
  final TokenService tokenService;
  final ImageService imageService;

  public NewsController(PostService postService, UserService userService, TokenService tokenService,
      ImageService imageService) {
    this.postService = postService;
    this.userService = userService;
    this.tokenService = tokenService;
    this.imageService = imageService;
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
  public String addPost(Model model, @Valid Post post,@RequestParam("files") MultipartFile file, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "addpost";
    } else {
      postService.addPost(post, file);
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
      List<Image> imageList = imageService.findAllByPost(post.get());
      model.addAttribute("post", post.get());
      model.addAttribute("imageList", imageList);
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

//  @RequestMapping(value = "/upload", method = RequestMethod.POST)
//  public String fileUpload(HttpServletRequest request, @RequestParam("file") MultipartFile file,
//      RedirectAttributes redirectAttributes, Model model) {
//    model.addAttribute("file", file);
//    String referer = request.getHeader("Referer");
//    redirectAttributes.addFlashAttribute("message",
//        "You successfully uploaded " + file.getOriginalFilename() + "!");
//    imageService.saveImage(file);
//    return "redirect:" + referer;
//  }
}
