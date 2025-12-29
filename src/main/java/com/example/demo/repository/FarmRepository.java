package com.example.demo.repository;

import com.example.demo.entity.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    @Query("SELECT f FROM Farm f WHERE f.owner.id = ?1")
    List<Farm> findByOwnerId(Long ownerId);
}