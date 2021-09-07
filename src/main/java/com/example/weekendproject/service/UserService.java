package com.example.weekendproject.service;

import com.example.weekendproject.model.Role;
import com.example.weekendproject.model.User;
import com.example.weekendproject.repository.RoleRepository;
import com.example.weekendproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public User addUser(User user) {
        user = addUserRole(user);
        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));
        return userRepository.save(user);
    }

    public boolean isUserAlreadyInBase(User user) {
        String name = user.getUsername();
        String email = user.getEmail();
        if (userRepository.findByUsername(name).isPresent() ||
        userRepository.findByEmail(email).isPresent()) {
            return true;
        }
        return false;
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
        if(authentication instanceof AnonymousAuthenticationToken) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isCurrentUserAdmin() {
        User user = getInstanceOfCurrentUser();
        Set<Role> roles = user.getRoles();
        Role admin = roleRepository.findByRole("ROLE_ADMIN");
        if(roles.contains(admin)) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isCurrentUserUser() {
        User user = getInstanceOfCurrentUser();
        Set<Role> roles = user.getRoles();
        Role admin = roleRepository.findByRole("ROLE_USER");
        if(roles.contains(admin)) {
            return true;
        } else {
            return false;
        }
    }
}
