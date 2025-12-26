package com.example.demo.repository;

import com.example.demo.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    @Query("SELECT s FROM Suggestion s WHERE s.farm.id = ?1")
    List<Suggestion> findByFarmId(Long farmId);
}