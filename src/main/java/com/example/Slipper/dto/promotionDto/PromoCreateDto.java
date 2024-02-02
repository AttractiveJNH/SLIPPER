package com.example.Slipper.dto.promotionDto;

import com.example.Slipper.entity.promotionEntity.Promotion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;


import java.time.LocalDate;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromoCreateDto { // 체험, 이벤트 홍보 게시판 글 작성 DTO


    private int promoBrdCategory; // 카테고리
    private LocalDate promoBrdApplyStartDate; // 체험 신청 시작일
    private LocalDate promoBrdApplyEndDate; // 체험 신청 마감일
    private LocalDate promoBrdEventStartDate; // 이벤트 시작일
    private LocalDate promoBrdEventEndDate; // 이벤트 마감일
    private LocalDate promoBrdExpSelect; // 체험단 선정일
    private int promoBrdMaxParticipants; //신청 가능 인원수
    private int promoBrdApplyParticipants; // 신청 인원수
    private String promoBrdBusinessName; // 상호명
    private LocalDate promoBrdExperienceDate; // 체험일
    private String promoBrdTitle; // 제목
    private String promoBrdContent; // 내용
    private String promoBrdMainImg; // 대표이미지
    private String promoBrdExperienceProvided; // 체험 제공
    private LocalDate promoBrdReviewStart; // 리뷰 작성 시작일
    private LocalDate promoBrdReviewDeadline; // 리뷰 작성 마감일
    private String promoBrdRegion; // 지역
    private String promoBrdArea; // 장소
    private int promoBrdViewCount; // 조회수

    public Promotion toEntity(String id){
        return new Promotion(null, id, promoBrdCategory, promoBrdApplyStartDate,
                promoBrdApplyEndDate, promoBrdEventStartDate, promoBrdEventEndDate, promoBrdExpSelect,
                promoBrdMaxParticipants, promoBrdApplyParticipants, promoBrdBusinessName,
                promoBrdExperienceDate, promoBrdTitle, promoBrdContent, promoBrdMainImg,
                promoBrdExperienceProvided, promoBrdReviewStart, promoBrdReviewDeadline, promoBrdRegion,
                promoBrdArea, promoBrdViewCount);
    }

}
