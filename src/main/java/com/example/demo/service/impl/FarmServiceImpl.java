package com.example.demo.service;

import com.example.demo.entity.Farm;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FarmRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {
    
    @Autowired
    private FarmRepository farmRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public FarmServiceImpl(FarmRepository farmRepository, UserRepository userRepository) {
        this.farmRepository = farmRepository;
        this.userRepository = userRepository;
    }
    
    @Override
    public Farm createFarm(Farm farm, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        if (farm.getSoilPH() < 4.0 || farm.getSoilPH() > 9.0) {
            throw new IllegalArgumentException("Invalid pH range");
        }
        
        farm.setOwner(owner);
        return farmRepository.save(farm);
    }
    
    @Override
    public Farm getFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found"));
    }
    
    @Override
    public List<Farm> getFarmsByOwner(Long ownerId) {
        return farmRepository.findByOwnerId(ownerId);
    }
}