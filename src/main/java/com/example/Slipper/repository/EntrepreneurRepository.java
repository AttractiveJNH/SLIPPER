package com.example.Slipper.repository;

import com.example.Slipper.entity.SswTestEntity.Entrepreneurs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepreneurRepository extends JpaRepository<Entrepreneurs, Integer> {
}
