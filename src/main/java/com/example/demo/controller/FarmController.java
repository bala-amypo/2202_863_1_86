package com.example.demo.controller;

import com.example.demo.dto.FarmRequest;
import com.example.demo.entity.Farm;
import com.example.demo.entity.User;
import com.example.demo.service.FarmService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/farms")
@RequiredArgsConstructor
@Tag(name = "Farms")
public class FarmController {
    private final FarmService farmService;
    private final UserService userService;

    @Operation(summary = "Create a farm for current user")
    @PostMapping
    public ResponseEntity<Farm> createFarm(@RequestBody FarmRequest req, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        var farm = Farm.builder()
                .name(req.getName())
                .soilPH(req.getSoilPH())
                .waterLevel(req.getWaterLevel())
                .season(req.getSeason())
                .build();
        Farm saved = farmService.createFarm(farm, userId);
        return ResponseEntity.ok(saved);
    }

    @Operation(summary = "List farms belonging to current user")
    @GetMapping
    public ResponseEntity<List<Farm>> listFarms(Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        return ResponseEntity.ok(farmService.getFarmsByOwner(userId));
    }

    @Operation(summary = "Get farm details by id (must belong to user or user admin)")
    @GetMapping("/{farmId}")
    public ResponseEntity<Farm> getFarm(@PathVariable Long farmId, Authentication auth) {
        Farm f = farmService.getFarmById(farmId);
        return ResponseEntity.ok(f);
    }
}