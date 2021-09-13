package com.example.weekendproject.repository;

import com.example.weekendproject.model.Token;
import com.example.weekendproject.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends CrudRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUsername(String username);

  Optional<User> findById(int id);

  Optional<User> findByToken(Token token);
}
