package com.example.Slipper.service;


import com.example.Slipper.entity.UserEntity;
import com.example.Slipper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {



    private final UserRepository userRepository;




    //아이디 중복체크
    /**
     * loginId 중복 체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     */
    public boolean checkLoginIdDuplicate(String userId){

        return userRepository.existsByUserId(userId);

    }

    /**
     * nickname 중복 체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     */
    public boolean checkNicknameDuplicate(String userNickName) {

        return userRepository.existsByUserNickName(userNickName);

    }


    public UserEntity getLoginUserByLoginId(String userId) {

        if(userId == null) return null;

        Optional<UserEntity> optionalUser = userRepository.findByUserId(userId);
        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }
}
