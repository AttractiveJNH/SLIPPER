package com.example.Slipper.dto.promotionDto;

import com.example.Slipper.entity.promotionEntity.PromotionBoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class PromoComntDto {

    private int promoBrdPostId; // 홍보 게시판 번호
    private String promoBrdComntContent; // 내용

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime promoBrdComntDate; // 작성일
    private String userNickName; // 유저닉네임
    private String entrepreNickName; // 사업자 닉네임
    private String userId; // 유저아이디
    private String entrepreId; // 사업자아이디


    //유저 아이디나 사업자 아이디 둘 중 하나를 받아서 아이디 값을 기준으로 DB에서 닉네임을 가져옴.
    public PromotionBoardComment toComnt() {
        return new PromotionBoardComment(promoBrdPostId, promoBrdComntContent, promoBrdComntDate, userNickName, entrepreNickName, userId, entrepreId);
    }

}