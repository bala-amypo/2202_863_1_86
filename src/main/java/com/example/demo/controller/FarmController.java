package com.example.demo.controller;

import com.example.demo.entity.Farm;
import com.example.demo.service.FarmService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/farms")
public class FarmController {
    
    private final FarmService farmService;
    
    public FarmController(FarmService farmService) {
        this.farmService = farmService;
    }
    
    @PostMapping
    public ResponseEntity<Farm> createFarm(@RequestBody Farm farm, @RequestParam Long ownerId) {
        Farm createdFarm = farmService.createFarm(farm, ownerId);
        return ResponseEntity.ok(createdFarm);
    }
    
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Farm>> getFarmsByOwner(@PathVariable Long ownerId) {
        List<Farm> farms = farmService.getFarmsByOwner(ownerId);
        return ResponseEntity.ok(farms);
    }
    
    @GetMapping("/{farmId}")
    public ResponseEntity<Farm> getFarmById(@PathVariable Long farmId) {
        Farm farm = farmService.getFarmById(farmId);
        return ResponseEntity.ok(farm);
    }
}