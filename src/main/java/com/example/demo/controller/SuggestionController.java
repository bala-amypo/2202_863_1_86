package com.example.demo.controller;

import com.example.demo.entity.Suggestion;
import com.example.demo.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {
    
    @Autowired
    private SuggestionService suggestionService;
    
    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }
    
    @PostMapping("/generate/{farmId}")
    public ResponseEntity<Suggestion> generate(@PathVariable Long farmId) {
        Suggestion suggestion = suggestionService.generateSuggestion(farmId);
        return ResponseEntity.ok(suggestion);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Suggestion> getSuggestion(@PathVariable Long id) {
        Suggestion suggestion = suggestionService.getSuggestion(id);
        return ResponseEntity.ok(suggestion);
    }
}