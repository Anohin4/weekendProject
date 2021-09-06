package com.example.weekendproject.service;

import com.example.weekendproject.model.Role;
import com.example.weekendproject.model.User;
import com.example.weekendproject.repository.RoleRepository;
import com.example.weekendproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public User addUser(User user) {
        user = addUserRole(user);
        return userRepository.save(user);
    }

    public boolean isUserInBase(User user) {
        String name = user.getUserName();
        String email = user.getEmail();
        if (userRepository.findByUserName(name).isPresent() ||
        userRepository.findByEmail(email).isPresent()) {
            return false;
        }
        return true;
    }

    public User addUserRole(User user) {
        Role role = roleRepository.findByRole("USER");
        user.setRoles(Set.of(role));
        return user;
    }
}
