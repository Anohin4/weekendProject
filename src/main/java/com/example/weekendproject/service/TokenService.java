package com.example.weekendproject.service;

import com.example.weekendproject.model.Token;
import com.example.weekendproject.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenService {
    @Autowired
    TokenRepository tokenRepository;

    public Token addToken(Token token) {
        token.setDateOfExpire(LocalDateTime.now().plusDays(1));
        return tokenRepository.save(token);
    }

    public boolean isExpired(Token token) {
        int compare = LocalDateTime.now().compareTo(token.getDateOfExpire());
        return compare > 0;
    }
}
