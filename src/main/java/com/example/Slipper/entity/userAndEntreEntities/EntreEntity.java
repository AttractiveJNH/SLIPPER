package com.example.Slipper.entity.userAndEntreEntities;

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

    @Column(name = "entrepre_name")
    private String entrepreName; // 대표자 성명

    @Column(name = "entrepre_reg_day", columnDefinition = "DATE")
    private LocalDate entrepreRegDay; //개업일자

    @Column(name = "entrepre_business_name")
    private String entrepreBusinessName; //상호명

    @Column(name = "entrepre_reg_num")
    private String entrepreRegNum; //사업자등록번호

    @Column(name = "entrepre_address")
    private String entrepreAddress; //사업장 주소지

    @Column(name="entrepre_id",unique = true)
    private String id; //대표자 아이디


    @Column(name="entrepre_password",unique = true)
    private String password; //대표자 비밀번호

    @Column(name = "entrepre_phone")
    private String entreprePhone; //대표자 전화번호

    @Column(name = "entrepre_location")
    private String entrepreLocation; //대표자 지역

    @Column(name = "entrepre_nick_name",unique = true)
    private String entrepreNickName; //대표자 닉네임

    private String role;

    @Column(name = "entrepre_birth_date", columnDefinition = "DATE")
    private String entrepreBirthDate; //대표자 생일

}
