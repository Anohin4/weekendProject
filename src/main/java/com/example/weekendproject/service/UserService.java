package com.example.weekendproject.service;

import com.example.weekendproject.model.Token;
import com.example.weekendproject.model.Post;
import com.example.weekendproject.model.Role;
import com.example.weekendproject.model.User;
import com.example.weekendproject.repository.RoleRepository;
import com.example.weekendproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

  final UserRepository userRepository;
  final RoleRepository roleRepository;
  final TokenService tokenService;
  final SendMailService sendMailService;
  final PasswordEncoder passwordEncoder;

  public UserService(TokenService tokenService, RoleRepository roleRepository,
      UserRepository userRepository, SendMailService sendMailService,
      PasswordEncoder passwordEncoder) {
    this.tokenService = tokenService;
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.sendMailService = sendMailService;
    this.passwordEncoder = passwordEncoder;
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User addUser(User user) {
    Token token = tokenService.setExpireDate(new Token(), 1);
    user = addUserRole(user);
    user.setToken(token);
    String password = user.getPassword();
    sendMailService.sendMail(user.getEmail(), token.getId());
    user.setPassword(passwordEncoder.encode(password));
    return userRepository.save(user);
  }

  public User reSendTokenEmail(String tokenId) {
    //finding user with that token
    Token oldToken = tokenService.findTokenById(tokenId).get();
    User user = findByToken(oldToken);
    //creating new token and deleting old one
    Token newToken = tokenService.setExpireDate(new Token(), 1);
    user.setToken(newToken);
    tokenService.deleteOldToken(tokenId);
    //sending email with new token
    sendMailService.sendMail(user.getEmail(), newToken.getId());
    return userRepository.save(user);
  }

  public User findByToken(Token token) {
    Optional<User> user = userRepository.findByToken(token);
    return user.get();
  }

  public User activateUser(User user) {
    user.setActive(true);
    return userRepository.save(user);
  }

  public boolean isUserAlreadyInBase(User user) {
    String name = user.getUsername();
    String email = user.getEmail();
    return userRepository.findByUsername(name).isPresent() ||
        userRepository.findByEmail(email).isPresent();
  }

  public User addUserRole(User user) {
    Role role = roleRepository.findByRole("ROLE_USER");
    user.setRoles(Set.of(role));
    return user;
  }

  public User getInstanceOfCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByUsername(authentication.getName()).get();
  }

  public boolean isAnon() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication instanceof AnonymousAuthenticationToken;
  }

  public void deletePost(User user, Post post) {
    List<Post> posts = user.getPosts();
    posts.remove(post);
    user.setPosts(posts);
    userRepository.save(user);
  }
}
