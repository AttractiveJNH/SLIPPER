package com.example.Slipper.repository;

import com.example.Slipper.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // userID가 중복하는가
    boolean existsByUserId(String userId);

    // nickName이 중복하는가
    boolean existsByUserNickName(String userNickName);

    UserEntity findByUserId(String userId);


    UserEntity findByUserNickName(String userNickName);
}
