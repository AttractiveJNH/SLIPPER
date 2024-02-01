package com.example.Slipper.repository;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.SswTestEntity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Integer> {




}
