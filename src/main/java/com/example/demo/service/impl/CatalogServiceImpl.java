package com.example.demo.service;

import com.example.demo.entity.Crop;
import com.example.demo.entity.Fertilizer;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.CropRepository;
import com.example.demo.repository.FertilizerRepository;
import com.example.demo.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {
    
    @Autowired
    private CropRepository cropRepository;
    
    @Autowired
    private FertilizerRepository fertilizerRepository;
    
    public CatalogServiceImpl(CropRepository cropRepository, FertilizerRepository fertilizerRepository) {
        this.cropRepository = cropRepository;
        this.fertilizerRepository = fertilizerRepository;
    }
    
    @Override
    public Crop addCrop(Crop crop) {
        if (crop.getSuitablePHMin() > crop.getSuitablePHMax()) {
            throw new BadRequestException("PH min cannot be greater than PH max");
        }
        if (!ValidationUtil.validSeason(crop.getSeason())) {
            throw new BadRequestException("Invalid season");
        }
        return cropRepository.save(crop);
    }
    
    @Override
    public Fertilizer addFertilizer(Fertilizer fertilizer) {
        if (!fertilizer.getNpkRatio().matches("\\d+-\\d+-\\d+")) {
            throw new BadRequestException("Invalid NPK ratio format");
        }
        return fertilizerRepository.save(fertilizer);
    }
    
    @Override
    public List<Crop> findSuitableCrops(Double pH, Double waterLevel, String season) {
        return cropRepository.findSuitableCrops(pH, season).stream()
                .filter(crop -> crop.getRequiredWater() <= waterLevel)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Fertilizer> findFertilizersForCrops(List<String> cropNames) {
        return cropNames.stream()
                .flatMap(cropName -> fertilizerRepository.findByCropName(cropName).stream())
                .distinct()
                .collect(Collectors.toList());
    }
}