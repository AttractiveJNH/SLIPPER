package com.example.Slipper.repository;

import com.example.Slipper.entity.SswTestEntity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {

}
