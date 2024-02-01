package com.example.Slipper.entity.SswTestEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="user_num")
    private Integer user_num;

    @Column(name ="user_id")
    private String user_id;

    @Column(name ="user_password")
    private String user_password;

    @Column(name ="user_name")
    private String user_name;

    @Column(name ="user_nick_name")
    private String user_nick_name;

    @Column(name ="user_birthdate")
    private LocalDate user_birthdate;

    @Column(name ="user_phone")
    private String user_phone;

    @Column(name ="user_location")
    private String user_location;

}
