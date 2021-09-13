package com.example.weekendproject.service;

import com.example.weekendproject.model.Post;
import com.example.weekendproject.model.User;
import com.example.weekendproject.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Component
public class PostService {

  final PostRepository postRepository;
  final UserService userService;

  public PostService(PostRepository postRepository, UserService userService) {
    this.postRepository = postRepository;
    this.userService = userService;
  }

  public Post addPost(Post post) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    post.setDate(now.format(formatter));
    post.setUser(userService.getInstanceOfCurrentUser());
    return postRepository.save(post);
  }

  public Page<Post> findAll(int page) {
    Pageable pageable = PageRequest.of(page, 10);
    return postRepository.findAll(pageable);
  }

  public Optional<Post> findPost(int id) {
    return postRepository.findById(id);
  }

  public void deletePost(int id) {
    Optional<Post> optionalPost = postRepository.findById(id);
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();
      User user = post.getUser();
      userService.deletePost(user, post);
      postRepository.delete(post);
    }
  }
}
