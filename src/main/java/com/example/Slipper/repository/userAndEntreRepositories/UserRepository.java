package com.example.Slipper.repository.userAndEntreRepositories;

import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // userID가 중복하는가
    boolean existsById(String id);

    // nickName이 중복하는가
    boolean existsByUserNickName(String userNickName);

    // id로 유저 정보 찾기
   Optional<UserEntity> findById(String id);

   // id 값을 가져올 때 사용됨
   UserEntity getById(String id);

   //탈퇴?
    Optional<UserEntity> deleteById(String id);
   UserEntity findByUserNum(Long userNum);


}
