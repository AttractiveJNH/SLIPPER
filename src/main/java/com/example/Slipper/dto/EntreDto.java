package com.example.Slipper.dto;


import com.example.Slipper.entity.EntreEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class EntreDto {

    private EntreEntity entreEntity;

    private Long entrepreNum; // 사업자번호(pk, surrogate key)

    private String entrepreName; // 대표자 성명

    private LocalDate entrepreRegDay; //개업일자

    private String entrepreBusinessName; //상호명

    private String entrepreRegNum; //사업자등록번호

    private String entrepreAddress; //사업장 주소지

    private String entrepreId; //대표자 아이디

    private String entreprePassword; //대표자 비밀번호

    private String entreprePhone; //대표자 전화번호

    private String entrepreLocation; //대표자 지역

    private String entrepreNickName; //대표자 닉네임

    private String role;

}
