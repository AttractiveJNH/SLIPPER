package com.example.Slipper.repository;

import com.example.Slipper.entity.EntreEntity;
import com.example.Slipper.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntreRepository extends JpaRepository<EntreEntity, Long> {

    // userID가 중복하는가
    boolean existsByEntrepreId(String entrepreId);

    // nickName이 중복하는가
    boolean existsByEntrepreNickName(String entrepreNickName);

    UserEntity findByEntrepreId(String entrepreId);
}
