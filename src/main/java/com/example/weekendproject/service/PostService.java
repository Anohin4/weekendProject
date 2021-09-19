package com.example.weekendproject.service;

import com.example.weekendproject.model.Image;
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
import org.springframework.web.multipart.MultipartFile;

@Component
public class PostService {

  final PostRepository postRepository;
  final UserService userService;
  final ImageService imageService;

  public PostService(PostRepository postRepository, UserService userService,
      ImageService imageService) {
    this.postRepository = postRepository;
    this.userService = userService;
    this.imageService = imageService;
  }

  public Post addPost(Post post, MultipartFile file) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    post.setDate(now.format(formatter));
    post.setUser(userService.getInstanceOfCurrentUser());
    imageService.saveImage(file, post);
    return postRepository.save(post);
  }

//  public List<Image> addImages(Post post, MultipartFile file) {
//    List<Image> imagesList = post.getImage();
//    imagesList.add(imageService.saveImage(file));
//
//    return imagesList;
//  }

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
