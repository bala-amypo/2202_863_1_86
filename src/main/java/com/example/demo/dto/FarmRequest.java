package com.example.demo.dto;

import com.example.demo.entity.Farm;
import com.example.demo.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FarmRequest {
    private String name;
    private Double soilPH;
    private Double waterLevel;
    private String season;

    public Farm toEntity(User owner) {
        return Farm.builder()
                .owner(owner)
                .name(this.name)
                .soilPH(this.soilPH)
                .waterLevel(this.waterLevel)
                .season(this.season)
                .build();
    }
}