package com.example.Slipper.entity.promotionEntity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


@Entity
@Table(name = "promotion_board")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class PromotionBoard {

    @Id
    @Column(name = "promobrdpostid")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 데이터베이스에 A.I설정 되어있어야 사용됨.
    private Integer promoBrdPostId; // 홍보 게시판 번호


//    @ManyToOne(targetEntity = EntreEntity.class)
    @Column(name = "entrepre_id") //외래키 설정 안된 컬럼에 @JoinColumn쓰면서 entrepreneur테이블에서 기본키를 찾기 때문에 에러가 발생함.
    private String id;

    private int promoBrdCategory; // 카테고리
    private LocalDate promoBrdApplyStartDate; // 체험 신청 시작일 Date타입을 모두 LocalDate로 수정함.
    private LocalDate promoBrdApplyEndDate; // 체험 신청 마감일
    private LocalDate promoBrdEventStartDate; // 이벤트 시작일

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate promoBrdEventEndDate; // 이벤트 마감일
    private LocalDate promoBrdExpSelect; // 체험단 선정일
    private int promoBrdMaxParticipants; //신청 가능 인원수
    private int promoBrdApplyParticipants; // 신청 인원수
    private String promoBrdBusinessName; // 상호명
    private LocalDate promoBrdExperienceDate; // 체험일
    private String promoBrdTitle; // 제목

    @Column(name = "promo_brd_content", columnDefinition = "TEXT")
    private String promoBrdContent; // 내용
    private String promoBrdMainImg; // 대표이미지
    private String promoBrdExperienceProvided; // 체험 제공
    private LocalDate promoBrdReviewStart; // 리뷰 작성 시작일
    private LocalDate promoBrdReviewDeadline; // 리뷰 작성 마감일
    private String promoBrdRegion; // 지역
    private String promoBrdArea; // 장소
    private int promoBrdViewCount; // 조회수



}
