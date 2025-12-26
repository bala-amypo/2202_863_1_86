package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "farm_id")
    private Farm farm;
    
    private String suggestedCrops;
    private String suggestedFertilizers;
    private LocalDateTime createdAt;
    
    public Suggestion() {}
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
    
    public static SuggestionBuilder builder() {
        return new SuggestionBuilder();
    }
    
    public static class SuggestionBuilder {
        private Long id;
        private Farm farm;
        private String suggestedCrops;
        private String suggestedFertilizers;
        private LocalDateTime createdAt;
        
        public SuggestionBuilder id(Long id) { this.id = id; return this; }
        public SuggestionBuilder farm(Farm farm) { this.farm = farm; return this; }
        public SuggestionBuilder suggestedCrops(String suggestedCrops) { this.suggestedCrops = suggestedCrops; return this; }
        public SuggestionBuilder suggestedFertilizers(String suggestedFertilizers) { this.suggestedFertilizers = suggestedFertilizers; return this; }
        public SuggestionBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        
        public Suggestion build() {
            Suggestion suggestion = new Suggestion();
            suggestion.id = this.id;
            suggestion.farm = this.farm;
            suggestion.suggestedCrops = this.suggestedCrops;
            suggestion.suggestedFertilizers = this.suggestedFertilizers;
            suggestion.createdAt = this.createdAt;
            return suggestion;
        }
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Farm getFarm() { return farm; }
    public void setFarm(Farm farm) { this.farm = farm; }
    public String getSuggestedCrops() { return suggestedCrops; }
    public void setSuggestedCrops(String suggestedCrops) { this.suggestedCrops = suggestedCrops; }
    public String getSuggestedFertilizers() { return suggestedFertilizers; }
    public void setSuggestedFertilizers(String suggestedFertilizers) { this.suggestedFertilizers = suggestedFertilizers; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}