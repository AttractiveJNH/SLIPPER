package com.example.Slipper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "user")
@Getter @NoArgsConstructor
@Setter
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num")
    private Long userNum;

    @Column(unique = true)
    private String userId;

    private String userPassword;

    private String userName;

    private LocalDate userBirthDate;

    private String userPhone;

    private String userLocation;

    @Column(unique = true)
    private String userNickName;

    private String role;


}
