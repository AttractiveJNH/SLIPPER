package com.example.Slipper.entity.SswTestEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "entrepreneurs")
public class Entrepreneur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="entrepre_num")
    private Integer entrepre_num;

    @Column(name ="entrepre_id")
    private String entrepre_id;

    @Column(name ="entrepre_password")
    private String entrepre_password;

    @Column(name ="entrepre_reg_day")
    private Date entrepre_reg_day;

    @Column(name ="entrepre_business_name")
    private String entrepre_business_name;

    @Column(name ="entrepre_address")
    private String entrepre_address;

    @Column(name ="entrepre_reg_num")
    private String entrepre_reg_num;

    @Column(name ="entrepre_name")
    private String entrepre_name;

    @Column(name ="entrepre_nick_name")
    private String entrepre_nick_name;

    @Column(name ="entrepre_phone")
    private String entrepre_phone;

    @Column(name ="entrepre_location")
    private String entrepre_location;

}
