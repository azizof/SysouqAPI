package com.sysouq.sysouqapi.services;


import com.sysouq.sysouqapi.entities.Token;
import com.sysouq.sysouqapi.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * add token to the database and bind it to the user with the given email
     *
     * @param tokenString the token as String value
     * @param email      the user email
     */
    public void addToken(String tokenString, String email) {
        Token token = new Token();
        token.setToken(tokenString);
        token.setEmail(email);

        tokenRepository.save(token);
    }

    /**
     * check if token is valid and for the correct user. after that
     * check if token is not expired.
     *
     * @param tokenString the token as String value
     * @param email      the user id
     * @return true if token is valid and bind to email
     */
    public boolean isTokenValid(String tokenString, String email) {
        Optional<Token> token = tokenRepository.findByTokenAndEmail(tokenString, email);
        if (token.isPresent()) {
            Token t = token.get();
            return LocalDateTime.now().isBefore(t.getExpire());
        }
        return false;
    }
}
