package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.security.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        User u = User.builder().name(req.getName()).email(req.getEmail()).password(req.getPassword()).build();
        User saved = userService.register(u);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "Login and receive JWT")
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        User u = userService.findByEmail(req.getEmail());
        
        var encoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        if (!encoder.matches(req.getPassword(), u.getPassword())) {
            return ResponseEntity.status(401).build();
        }
        String token = jwtTokenProvider.createToken(u.getId(), u.getEmail(), u.getRole());
        return ResponseEntity.ok(new AuthResponse(token, u.getId(), u.getEmail(), u.getRole()));
    }
}