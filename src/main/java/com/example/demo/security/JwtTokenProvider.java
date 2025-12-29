package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {

    // In production keep secret in env / vault
    private final Key key = Keys.hmacShaKeyFor("VerySecretKeyThatShouldBeAtLeast256BitsLongForHS256!".getBytes());
    private final long validityInMilliseconds = 1000L * 60 * 60 * 24; // 24h

    public String createToken(Long userId, String email, String role) {
        Claims claims = Jwts.claims();
        claims.put("userId", userId);
        claims.put("email", email);
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public Long getUserId(String token) {
        Claims c = validateToken(token).getBody();
        return ((Number) c.get("userId")).longValue();
    }

    public String getEmail(String token) {
        return (String) validateToken(token).getBody().get("email");
    }

    public String getRole(String token) {
        return (String) validateToken(token).getBody().get("role");
    }
}