package com.example.Slipper.entity.generalEntity;

import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "generalboard")
public class GeneralBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="generalnum")
    private Integer generalNum;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_num", referencedColumnName = "user_num")
    private UserEntity user_num;

    @ManyToOne(targetEntity = EntreEntity.class)
    @JoinColumn(name = "entrepre_num", referencedColumnName = "entrepre_num")
    private EntreEntity entrepre_num;

    @Column(name ="general_category")
    private int general_category;

    @Column(name ="general_title")
    private String general_title;

    @Column(name ="general_content")
    private String general_content;

    @Column(name ="general_views")
    private int general_views;

    @CreatedDate
    @Column(name ="general_write_date")
    private LocalDateTime general_write_date;

    @Column(name ="general_nick_name")
    private String general_nick_name;

}
