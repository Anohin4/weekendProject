package com.example.weekendproject.service;

import com.example.weekendproject.model.User;
import com.example.weekendproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {
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
}
