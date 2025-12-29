package com.example.demo.service;

import com.example.demo.entity.Farm;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FarmRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Farm createFarm(Farm farm) {

        // 1️⃣ Get logged-in user from JWT
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName(); // comes from JWT subject

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // 2️⃣ Validate soil pH
        if (farm.getSoilPH() < 4.0 || farm.getSoilPH() > 9.0) {
            throw new IllegalArgumentException("Invalid pH range");
        }

        // 3️⃣ Set owner (THIS FIXES 500 ERROR)
        farm.setOwner(owner);

        return farmRepository.save(farm);
    }

    @Override
    public Farm getFarmById(Long id) {
        return farmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Farm not found"));
    }

    @Override
    public List<Farm> getMyFarms() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        String email = auth.getName();

        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return farmRepository.findByOwner(owner);
    }
}
