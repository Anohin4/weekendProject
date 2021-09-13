package com.example.weekendproject;

import com.example.weekendproject.model.Post;
import com.example.weekendproject.model.Role;
import com.example.weekendproject.model.User;
import com.example.weekendproject.repository.PostRepository;
import com.example.weekendproject.repository.RoleRepository;
import com.example.weekendproject.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class WeekendProjectApplication {

  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
  final RoleRepository roleRepository;
  final UserRepository userRepository;
  final PostRepository postRepository;

  public WeekendProjectApplication(RoleRepository roleRepository, UserRepository userRepository,
      PostRepository postRepository) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.postRepository = postRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(WeekendProjectApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void rolesOnStart() throws Exception {
    Role role = new Role(1, "ROLE_ADMIN");
    Role role2 = new Role(2, "ROLE_USER");
    roleRepository.save(role);
    roleRepository.save(role2);
    Set<Role> roles = Set.of(role, role2);
    User user = new User();
    user.setId(1);
    user.setPassword(bCryptPasswordEncoder.encode("adminadmin"));
    user.setRoles(roles);
    user.setUsername("admin");
    user.setEmail("admin@admin");
    userRepository.save(user);
    user.setActive(true);

    Post post = new Post();
    post.setUser(user);

    int n = 1;
    while (n != 45) {
      String postText = "Post " + n;
      post.setId(n);
      post.setNews(postText);
      postRepository.save(post);
      n++;
    }
  }
}
