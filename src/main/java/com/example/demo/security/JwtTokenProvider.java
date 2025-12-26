package com.example.demo.security;

import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    private final long validityInMilliseconds = 3600000; // 1h
    private final Map<String, Map<String, Object>> tokenStore = new HashMap<>();
    
    public String createToken(Long userId, String email, String role) {
        String token = "token_" + userId + "_" + System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("role", role);
        claims.put("exp", new Date(System.currentTimeMillis() + validityInMilliseconds));
        tokenStore.put(token, claims);
        return token;
    }
    
    public boolean validateToken(String token) {
        if (!tokenStore.containsKey(token)) return false;
        Date exp = (Date) tokenStore.get(token).get("exp");
        return exp.after(new Date());
    }
    
    public String getEmail(String token) {
        return (String) tokenStore.get(token).get("email");
    }
    
    public Long getUserId(String token) {
        return (Long) tokenStore.get(token).get("userId");
    }
    
    public String getRole(String token) {
        return (String) tokenStore.get(token).get("role");
    }
}