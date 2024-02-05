package com.example.Slipper.dto;


import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class EntreDto {

    private EntreEntity entreEntity;

    private Long entrepreNum; // 사업자번호(pk, surrogate key)

    @NotBlank(message = "이름을 입력해주세요.")
    private String entrepreName; // 대표자 성명

    @NotNull(message = "개업일자를 입력해주세요.")
    private LocalDate entrepreRegDay; //개업일자

    @NotBlank(message = "상호명을 입력해주세요.")
    private String entrepreBusinessName; //상호명

    @NotBlank(message = "사업자 등록 번호를 입력해주세요.")
    private String entrepreRegNum; //사업자등록번호

    @NotBlank(message = "사업장 주소지를 입력해주세요.")
    private String entrepreAddress; //사업장 주소지

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 6 , max = 15, message = "아이디는 6글자 이상 15 글자 이하로 아이디를 만들어주세요.")
    private String id; //대표자 아이디

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, max = 12, message = "비밀번호는 6글자 이상 12글자 이하로 입력해주세요.")
    private String password; //대표자 비밀번호
    
    private String passwordCheck; // 비밀번호 확인

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "(01[0])(\\d{4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String entreprePhone; //대표자 전화번호

    @NotBlank(message = "지역을 선택해주세요.")
    private String entrepreLocation; //대표자 지역

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min=3 , max = 10 , message = "닉네임은 3글자 이상 10글자 이하로 입력해주세요.")
    private String entrepreNickName; //대표자 닉네임

    private String role;

    @NotNull(message = "생일을 입력해주세요.")
    private LocalDate entrepreBirthDate; //대표자 생일
    
    

}
