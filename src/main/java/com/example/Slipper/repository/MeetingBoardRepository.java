package com.example.Slipper.repository;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingBoardRepository extends JpaRepository<MeetingBoard, Integer> {
}
