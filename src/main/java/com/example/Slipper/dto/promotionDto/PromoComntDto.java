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
    private String userId; // 유저 등록 번호
    private String entrepreId;
    private String promoBrdComntContent; // 내용

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime promoBrdComntDate; // 작성일
    private String userNickName; // 유저닉네임
    private String entrepreNickName;




    public PromotionBoardComment toComnt() {
        return new PromotionBoardComment(promoBrdPostId, userId, entrepreId, promoBrdComntContent, promoBrdComntDate, userNickName, entrepreNickName);
    }
}
