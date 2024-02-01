package com.example.Slipper.service.loginAndJoinServices;

import com.example.Slipper.dto.EntreDto;
import com.example.Slipper.dto.UserDto;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.userAndEntreRepositories.EntreRepository;
import com.example.Slipper.repository.userAndEntreRepositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
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



        user.setId(userDto.getId());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setUserBirthDate(userDto.getUserBirthDate());
        user.setUserName(userDto.getUserName());
         user.setUserLocation(userDto.getUserLocation() != null ? userDto.getUserLocation().replace(",","") : null);
        user.setUserPhone(userDto.getUserPhone());
        user.setUserNickName(userDto.getUserNickName());
        user.setRole("USER");

        userRepository.save(user);
    }



    //사업자 회원가입
    public void entreJoinProcess(EntreDto entreDto) {


        EntreEntity entre = new EntreEntity();
        entre.setId(entreDto.getId());
        entre.setPassword(passwordEncoder.encode(entreDto.getPassword()));
        entre.setEntrepreName(entreDto.getEntrepreName());
        entre.setEntrepreLocation(entreDto.getEntrepreLocation() != null ? entreDto.getEntrepreLocation().replace(",","") : null);
        entre.setEntreprePhone(entreDto.getEntreprePhone());
        entre.setEntrepreNickName(entreDto.getEntrepreNickName());
        entre.setEntrepreRegNum(entreDto.getEntrepreRegNum());
        entre.setEntrepreRegDay(entreDto.getEntrepreRegDay());
        entre.setEntrepreBusinessName(entreDto.getEntrepreBusinessName());
        entre.setEntrepreAddress(entreDto.getEntrepreAddress());
        entre.setRole("ENTREPRENEUR");

        entreRepository.save(entre);
    }

    
    // 비밀번호 암호화 빈

}
