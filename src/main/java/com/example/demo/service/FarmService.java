package com.example.demo.service;

import com.example.demo.entity.Farm;
import java.util.List;

public interface FarmService {

    Farm createFarm(Farm farm);      // owner comes from JWT
    Farm getFarmById(Long id);
    List<Farm> getMyFarms();          // farms of logged-in user
}
