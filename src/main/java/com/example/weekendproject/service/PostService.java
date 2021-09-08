package com.example.weekendproject.service;

import com.example.weekendproject.model.Post;
import com.example.weekendproject.repository.PostRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostService {
    final
    PostRepository postRepository;
    final
    UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post addPost(Post post) {
        post.setDate(LocalDateTime.now().toString());
        post.setUser(userService.getInstanceOfCurrentUser());
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }


}
