package com.example.demo.service.impl;

import com.example.demo.entity.Crop;
import com.example.demo.entity.Farm;
import com.example.demo.entity.Fertilizer;
import com.example.demo.entity.Suggestion;
import com.example.demo.repository.SuggestionRepository;
import com.example.demo.service.CatalogService;
import com.example.demo.service.FarmService;
import com.example.demo.service.SuggestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SuggestionServiceImpl implements SuggestionService {
    
    private final SuggestionRepository suggestionRepository;
    private final FarmService farmService;
    private final CatalogService catalogService;
    
    public SuggestionServiceImpl(SuggestionRepository suggestionRepository, 
                                FarmService farmService, 
                                CatalogService catalogService) {
        this.suggestionRepository = suggestionRepository;
        this.farmService = farmService;
        this.catalogService = catalogService;
    }
    
    @Override
    public Suggestion generateSuggestion(Long farmId) {
        Farm farm = farmService.getFarmById(farmId);
        
        List<Crop> suitableCrops = catalogService.findSuitableCrops(
                farm.getSoilPH(), farm.getWaterLevel(), farm.getSeason());
        
        List<String> cropNames = suitableCrops.stream()
                .map(Crop::getName)
                .collect(Collectors.toList());
        
        List<Fertilizer> fertilizers = catalogService.findFertilizersForCrops(cropNames);
        
        String suggestedCrops = String.join(",", cropNames);
        String suggestedFertilizers = fertilizers.stream()
                .map(Fertilizer::getName)
                .collect(Collectors.joining(","));
        
        Suggestion suggestion = Suggestion.builder()
                .farm(farm)
                .suggestedCrops(suggestedCrops)
                .suggestedFertilizers(suggestedFertilizers)
                .build();
        
        return suggestionRepository.save(suggestion);
    }
    
    @Override
    public Suggestion getSuggestion(Long suggestionId) {
        return suggestionRepository.findById(suggestionId)
                .orElseThrow(() -> new RuntimeException("Suggestion not found"));
    }
    
    @Override
    public List<Suggestion> getSuggestionsByFarm(Long farmId) {
        return suggestionRepository.findByFarmId(farmId);
    }
}