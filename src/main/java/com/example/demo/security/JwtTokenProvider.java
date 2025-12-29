package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    // ✅ Secret key for signing JWT
    private final String secretKey = "my_super_secret_key_98765";

    // Token validity: 1 hour
    private final long validityInMilliseconds = 3600000;

    // Create JWT token
    public String createToken(Long userId, String email, String role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("userId", userId);
        claims.put("role", role);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extract email
    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Extract user ID
    public Long getUserId(String token) {
        Object id = getClaims(token).get("userId");
        return id instanceof Integer ? ((Integer) id).longValue() : (Long) id;
    }

    // Extract role
    public String getRole(String token) {
        return (String) getClaims(token).get("role");
    }

    // Helper to get claims
    private Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
