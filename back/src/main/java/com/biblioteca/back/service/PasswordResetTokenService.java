package com.biblioteca.back.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    
    private final Map<String, TokenData> tokenStore = new HashMap<>();

    public String generarToken(String username) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, new TokenData(username, LocalDateTime.now().plusMinutes(30)));
        return token;
    }

    public String getUsernameFromToken(String token) {
        TokenData data = tokenStore.get(token);
        if (data == null || data.expiration.isBefore(LocalDateTime.now())) {
            return null;
        }
        return data.username;
    }

    public void eliminarToken(String token) {
        tokenStore.remove(token);
    }

    private static class TokenData {
        String username;
        LocalDateTime expiration;

        TokenData(String username, LocalDateTime expiration) {
            this.username = username;
            this.expiration = expiration;
        }
    }
}
