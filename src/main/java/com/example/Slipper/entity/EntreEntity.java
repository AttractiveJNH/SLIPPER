package com.example.Slipper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;



@Entity
@Table(name="entrepreneur")
@Getter @Setter
@NoArgsConstructor
public class EntreEntity {


//    private EntreEntity entreEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entrepre_num")
    private Long entrepreNum; // 사업자번호(pk, surrogate key)

    private String entrepreName; // 대표자 성명

    @Column(columnDefinition = "DATE")
    private LocalDate entrepreRegDay; //개업일자

    private String entrepreBusinessName; //상호명

    private String entrepreRegNum; //사업자등록번호

    private String entrepreAddress; //사업장 주소지

    @Column(unique = true)
    private String entrepreId; //대표자 아이디

    private String entreprePassword; //대표자 비밀번호

    private String entreprePhone; //대표자 전화번호

    private String entrepreLocation; //대표자 지역

    @Column(unique = true)
    private String entrepreNickName; //대표자 닉네임

    private String role;

}
