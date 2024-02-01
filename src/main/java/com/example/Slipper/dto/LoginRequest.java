package com.example.Slipper.dto;

//유저 로그인 폼

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class LoginRequest {

    private String loginId;

    private String password;

    private String userType;


    // 로그인 객체를 반환할 때 비밀번호를 지운다.
    public void clearPassword(){
        this.password = "";
    }
//
//    public String login(String loginId){
//        this.loginId = loginId;
//
//        return loginId;
//    }


}
