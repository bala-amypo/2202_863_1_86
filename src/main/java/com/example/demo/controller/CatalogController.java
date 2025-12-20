package com.example.demo.controller;

import com.example.demo.entity.Crop;
import com.example.demo.entity.Fertilizer;
import com.example.demo.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    
    private final CatalogService catalogService;
    
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }
    
    @PostMapping("/crop")
    public ResponseEntity<Crop> addCrop(@RequestBody Crop crop) {
        Crop addedCrop = catalogService.addCrop(crop);
        return ResponseEntity.ok(addedCrop);
    }
    
    @PostMapping("/fertilizer")
    public ResponseEntity<Fertilizer> addFertilizer(@RequestBody Fertilizer fertilizer) {
        Fertilizer addedFertilizer = catalogService.addFertilizer(fertilizer);
        return ResponseEntity.ok(addedFertilizer);
    }
    
    @GetMapping("/crops/suitable")
    public ResponseEntity<List<Crop>> findSuitableCrops(
            @RequestParam Double ph,
            @RequestParam Double water,
            @RequestParam String season) {
        List<Crop> crops = catalogService.findSuitableCrops(ph, water, season);
        return ResponseEntity.ok(crops);
    }
    
    @GetMapping("/fertilizers/by-crop")
    public ResponseEntity<List<Fertilizer>> findFertilizersByCrop(@RequestParam String name) {
        List<Fertilizer> fertilizers = catalogService.findFertilizersForCrops(List.of(name));
        return ResponseEntity.ok(fertilizers);
    }
}