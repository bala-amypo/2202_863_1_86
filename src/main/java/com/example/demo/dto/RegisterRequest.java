package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String email;
    private String password;

    public User toEntity(String role) {
        return User.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .role(role)
                .build();
    }
}