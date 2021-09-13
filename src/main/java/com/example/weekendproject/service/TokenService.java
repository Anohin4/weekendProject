package com.example.weekendproject.service;

import com.example.weekendproject.model.Token;
import com.example.weekendproject.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenService {

  final TokenRepository tokenRepository;

  public TokenService(TokenRepository tokenRepository) {
    this.tokenRepository = tokenRepository;
  }

  public Token setExpireDate(Token token, int days) {
    token.setDateOfExpire(LocalDateTime.now().plusDays(days));
    //for testing
//        token.setDateOfExpire(LocalDateTime.now().plusSeconds(days));
    return token;
  }

  public boolean isExpired(Token token) {
    int compare = LocalDateTime.now().compareTo(token.getDateOfExpire());
    return compare > 0;
  }

  public Optional<Token> findTokenById(String id) {
    return tokenRepository.findById(id);
  }


  public void deleteOldToken(String id) {
    Optional<Token> token = tokenRepository.findById(id);
    token.ifPresent(x -> tokenRepository.delete(x));
  }
}
