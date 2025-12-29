package com.example.demo.security;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {
    private final UserRepository userRepository;

    public User loadById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}