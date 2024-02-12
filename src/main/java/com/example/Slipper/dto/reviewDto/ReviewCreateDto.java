package com.example.Slipper.dto.reviewDto;

import com.example.Slipper.entity.reviewEntity.ReviewBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewCreateDto {

    private int revBrdCategory;
    private String revBrdTitle;
    private String revBrdContent;
    private LocalDate revBrdRegDate;
    private String revBrdRegion;
    private String revBrdArea;
    private int revBrdViews;



    // 리뷰 게시글 작성자는 일반유저만 가능
    public ReviewBoard toEntity(String userId){
        return new ReviewBoard(null, userId, revBrdCategory, revBrdTitle, revBrdContent, revBrdRegDate,revBrdRegion, revBrdArea, revBrdViews);
    }
}
