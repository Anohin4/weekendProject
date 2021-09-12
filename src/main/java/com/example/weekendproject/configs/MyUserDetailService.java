package com.example.weekendproject.configs;

import com.example.weekendproject.model.User;
import com.example.weekendproject.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserDetailService implements UserDetailsService {
    final
    UserService userService;

    public MyUserDetailService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       Optional<User> user = userService.findByUsername(s);
       if(user.isEmpty()) {
           throw  new UsernameNotFoundException(s);
       }
       return new UserDetailsImpl(user.get());
    }
}
