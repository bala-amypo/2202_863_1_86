package com.example.demo.entity;

import jakarta.persistence.*;


@Entity
public class Farm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private Double soilPH;
    private Double waterLevel;
    private String season;
    
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    
    public Farm() {}
    
    public static FarmBuilder builder() {
        return new FarmBuilder();
    }
    
    public static class FarmBuilder {
        private Long id;
        private String name;
        private Double soilPH;
        private Double waterLevel;
        private String season;
        private User owner;
        
        public FarmBuilder id(Long id) { this.id = id; return this; }
        public FarmBuilder name(String name) { this.name = name; return this; }
        public FarmBuilder soilPH(Double soilPH) { this.soilPH = soilPH; return this; }
        public FarmBuilder waterLevel(Double waterLevel) { this.waterLevel = waterLevel; return this; }
        public FarmBuilder season(String season) { this.season = season; return this; }
        public FarmBuilder owner(User owner) { this.owner = owner; return this; }
        
        public Farm build() {
            Farm farm = new Farm();
            farm.id = this.id;
            farm.name = this.name;
            farm.soilPH = this.soilPH;
            farm.waterLevel = this.waterLevel;
            farm.season = this.season;
            farm.owner = this.owner;
            return farm;
        }
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Double getSoilPH() { return soilPH; }
    public void setSoilPH(Double soilPH) { this.soilPH = soilPH; }
    public Double getWaterLevel() { return waterLevel; }
    public void setWaterLevel(Double waterLevel) { this.waterLevel = waterLevel; }
    public String getSeason() { return season; }
    public void setSeason(String season) { this.season = season; }
    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }
}