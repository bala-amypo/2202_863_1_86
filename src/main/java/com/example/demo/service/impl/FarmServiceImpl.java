package com.example.demo.service;

import com.example.demo.entity.Farm;
import com.example.demo.entity.User;
import com.example.demo.repository.FarmRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepository;
    private final UserRepository userRepository;

    @Override
    public Farm createFarm(Farm farm, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));
        // validation
        if (farm.getSoilPH() < 3.0 || farm.getSoilPH() > 10.0) {
            throw new IllegalArgumentException("pH must be between 3 and 10");
        }
        farm.setOwner(owner);
        return farmRepository.save(farm);
    }

    @Override
    public List<Farm> getFarmsByOwner(Long ownerId) {
        return farmRepository.findByOwnerId(ownerId);
    }

    @Override
    public Farm getFarmById(Long farmId) {
        return farmRepository.findById(farmId).orElseThrow(() -> new ResourceNotFoundException("Farm not found"));
    }
}