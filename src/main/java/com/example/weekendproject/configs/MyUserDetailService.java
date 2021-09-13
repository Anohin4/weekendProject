package com.example.weekendproject.configs;

import com.example.weekendproject.model.User;
import com.example.weekendproject.repository.UserRepository;
import com.example.weekendproject.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailService implements UserDetailsService {

  final UserRepository userRepository;

  public MyUserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(s);
    if (user.isEmpty()) {
      throw new UsernameNotFoundException(s);
    }
    return new UserDetailsImpl(user.get());
  }
}
