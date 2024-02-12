package com.example.Slipper.entity.reviewEntity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "review_board")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class ReviewBoard {

    @Id
    @Column(name = "revbrdpostid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer revBrdPostId;

    @Column(name = "user_id")
    private String userId;

    private int revBrdCategory; // 카테고리(일반, 체험, 이벤트)
    private String revBrdTitle; // 제목
    private String revBrdContent; // 내용

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate revBrdRegDate; // 등록시간

    private String revBrdRegion; // 지역구
    private String revBrdArea; // 장소

    private int revBrdViews; // 조회수


}
