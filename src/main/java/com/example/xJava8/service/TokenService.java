package com.example.xJava8.service;

import com.example.xJava8.entity.Token;
import com.example.xJava8.repository.jpa.JpaTokenRepository;
import com.example.xJava8.repository.jpa.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {
    @Autowired
    private JpaUserRepository jpaUserRepository;
    @Autowired
    private JpaTokenRepository jpaTokenRepository;

    public String createUnlockToken(Integer id) {
        String token = UUID.randomUUID().toString();

        Token unlockToken = new Token();
        unlockToken.setUserId(id);
        unlockToken.setToken(token);
        unlockToken.setExpirationDate(LocalDateTime.now().plusMinutes(5));
        jpaTokenRepository.save(unlockToken);

        return token;
    }

    public Token findByNameAndToken(String name, String token) {
        List<Token> tokens = jpaTokenRepository.findByNameAndToken(name, token);

        if (tokens == null || tokens.isEmpty()) {
            return null;
        }
        return tokens.get(0);
    }


    public void deleteByUserId(Integer userId) {
        jpaTokenRepository.deleteByUserId(userId);
    }
}
