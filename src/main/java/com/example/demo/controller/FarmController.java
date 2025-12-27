package com.example.demo.controller;

import com.example.demo.dto.FarmRequest;
import com.example.demo.entity.Farm;
import com.example.demo.service.FarmService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
public class FarmController {

    @Autowired
    private FarmService farmService;

    @Autowired
    private UserService userService;

    // ✅ CREATE FARM
    @PostMapping
    public ResponseEntity<Farm> createFarm(
            @RequestBody FarmRequest request,
            @AuthenticationPrincipal User user
    ) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        String email = user.getUsername(); // JWT subject
        Long userId = userService.getUserIdByEmail(email);

        Farm farm = Farm.builder()
                .name(request.getName())
                .soilPH(request.getSoilPH())
                .waterLevel(request.getWaterLevel())
                .season(request.getSeason())
                .build();

        Farm savedFarm = farmService.createFarm(farm, userId);
        return ResponseEntity.ok(savedFarm);
    }

    // ✅ LIST FARMS
    @GetMapping
    public ResponseEntity<List<Farm>> listFarms(
            @AuthenticationPrincipal User user
    ) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        String email = user.getUsername();
        Long userId = userService.getUserIdByEmail(email);

        return ResponseEntity.ok(farmService.getFarmsByOwner(userId));
    }

    // ✅ GET FARM BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Farm> getFarm(@PathVariable Long id) {
        return ResponseEntity.ok(farmService.getFarmById(id));
    }
}
