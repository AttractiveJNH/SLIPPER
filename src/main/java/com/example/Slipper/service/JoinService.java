package com.example.Slipper.service;

import com.example.Slipper.dto.EntreDto;
import com.example.Slipper.dto.UserDto;
import com.example.Slipper.entity.EntreEntity;
import com.example.Slipper.entity.UserEntity;

import com.example.Slipper.repository.EntreRepository;
import com.example.Slipper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JoinService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    EntreRepository entreRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    // User 회원 가입 메서드
    public void userJoinProcess(UserDto userDto) {


        UserEntity user = new UserEntity();


        validateDuplicateUser(user);
        user.setUserId(userDto.getUserId());
        user.setUserPassword(passwordEncoder.encode(userDto.getUserPassword()));
        user.setUserBirthDate(userDto.getUserBirthDate());
        user.setUserName(userDto.getUserName());
        user.setUserLocation(userDto.getUserLocation());
        user.setUserPhone(userDto.getUserPhone());
        user.setUserNickName(userDto.getUserNickName());
        user.setRole("USER");

        userRepository.save(user);
    }

    private void validateDuplicateUser(UserEntity user){
        UserEntity findUser = userRepository.findByUserId(user.getUserId());
        if(findUser != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    private void validateDuplicateUserNickName(UserEntity user){
        UserEntity findUser = userRepository.findByUserNickName(user.getUserNickName());
        if(findUser != null){
            throw new IllegalStateException("이미 존재하는 닉네임입니다.");
        }
    }


    //사업자 회원가입
    public void entreJoinProcess(EntreDto entreDto) {


        EntreEntity entre = new EntreEntity();
        entre.setEntrepreId(entreDto.getEntrepreId());
        entre.setEntreprePassword(passwordEncoder.encode(entreDto.getEntreprePassword()));
        entre.setEntrepreName(entreDto.getEntrepreName());
        entre.setEntrepreLocation(entreDto.getEntrepreLocation());
        entre.setEntreprePhone(entreDto.getEntreprePhone());
        entre.setEntrepreNickName(entreDto.getEntrepreNickName());
        entre.setEntrepreRegNum(entreDto.getEntrepreRegNum());
        entre.setEntrepreRegDay(entreDto.getEntrepreRegDay());
        entre.setEntrepreBusinessName(entreDto.getEntrepreBusinessName());
        entre.setEntrepreAddress(entreDto.getEntrepreAddress());
        entre.setRole("ENTREPRENEUR");

        entreRepository.save(entre);
    }


}
