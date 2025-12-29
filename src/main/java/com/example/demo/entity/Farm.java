package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Farm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private User owner;

    private String name;

    @Column(nullable = false)
    private Double soilPH;

    @Column(nullable = false)
    private Double waterLevel;

    @Column(nullable = false)
    private String season;
}