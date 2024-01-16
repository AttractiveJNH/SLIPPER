package com.example.Slipper.dto;

import com.example.Slipper.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter

public class UserDto {
    private UserEntity userEntity;


    private Long userNum;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 6 , max = 15, message = "아이디는 6글자 이상 15 글자 이하로 아이디를 만들어주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, max = 12, message = "비밀번호는 6글자 이상 12글자 이하로 입력해주세요.")
    private String userPassword;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "생일을 입력해주세요.")
    private LocalDate userBirthDate;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "(01[0])(\\d{4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String userPhone;

    @NotBlank(message = "지역을 선택해주세요.")
    private String userLocation;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min=3 , max = 10 , message = "닉네임은 3글자 이상 10글자 이하로 입력해주세요.")
    private String userNickName;

    private String role;
}
