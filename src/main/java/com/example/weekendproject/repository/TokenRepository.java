package com.example.weekendproject.repository;

import com.example.weekendproject.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TokenRepository extends CrudRepository<Token, String> {
    public Token findByUUID(String UUID);
}
