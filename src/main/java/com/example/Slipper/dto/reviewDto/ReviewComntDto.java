package com.example.Slipper.dto.reviewDto;



import com.example.Slipper.entity.reviewEntity.ReviewBoardComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
public class ReviewComntDto {

    private int revBrdPostId; // 리뷰 게시판 번호
    private String revBrdComntContent; // 내용

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime revBrdComntDate; // 작성일
    private String userNickName; // 유저닉네임
    private String entrepreNickName; // 사업자 닉네임
    private String userId; // 유저아이디
    private String entrepreId; // 사업자아이디


    //유저 아이디나 사업자 아이디 둘 중 하나를 받아서 아이디 값을 기준으로 DB에서 닉네임을 가져옴.
    public ReviewBoardComment toComnt() {
        return new ReviewBoardComment(revBrdPostId, revBrdComntContent, revBrdComntDate, userNickName, entrepreNickName, userId, entrepreId);
    }
}
