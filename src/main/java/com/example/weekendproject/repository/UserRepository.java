package com.example.weekendproject.repository;

import com.example.weekendproject.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByEmail(String email);
    public User findByUserName(String username);
}
