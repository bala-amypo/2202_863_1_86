package com.example.demo.controller;

import com.example.demo.dto.FarmRequest;
import com.example.demo.entity.Farm;
import com.example.demo.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/farms")
public class FarmController {

    @Autowired
    private FarmService farmService;

    @PostMapping
    public ResponseEntity<Farm> createFarm(@RequestBody FarmRequest request) {

        Farm farm = Farm.builder()
                .name(request.getName())
                .soilPH(request.getSoilPH())
                .waterLevel(request.getWaterLevel())
                .season(request.getSeason())
                .build();

        Farm savedFarm = farmService.createFarm(farm);
        return ResponseEntity.ok(savedFarm);
    }

    @GetMapping
    public ResponseEntity<List<Farm>> listFarms() {
        List<Farm> farms = farmService.getMyFarms();
        return ResponseEntity.ok(farms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Farm> getFarm(@PathVariable Long id) {
        Farm farm = farmService.getFarmById(id);
        return ResponseEntity.ok(farm);
    }
}
