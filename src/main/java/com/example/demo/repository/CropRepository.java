package com.example.demo.repository;

import com.example.demo.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    @Query("SELECT c FROM Crop c WHERE c.suitablePHMin <= ?1 AND c.suitablePHMax >= ?1 AND c.season = ?2")
    List<Crop> findSuitableCrops(Double pH, String season);
}