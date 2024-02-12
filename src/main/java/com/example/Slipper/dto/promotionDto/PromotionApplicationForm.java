package com.example.Slipper.dto.promotionDto;



import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import com.example.Slipper.entity.promotionEntity.PromotionBoardApplication;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PromotionApplicationForm {

    private Integer promotion_apply_status;

    private String nick_name;

    private LocalDate birthdate;

    // 일반 유저 모임 게시판 신청
    public PromotionBoardApplication userMeetApplication(UserEntity user_num, PromotionBoard promo_brd_post_id){
        return new PromotionBoardApplication(null, user_num, null, promo_brd_post_id,
                promotion_apply_status, null, nick_name, birthdate);
    }

    // 사업자 유저 모임 게시판 신청
    public PromotionBoardApplication entrepreneurMeetApplication(EntreEntity entrepre_num, PromotionBoard promo_brd_post_id){
        return new PromotionBoardApplication(null, null, entrepre_num, promo_brd_post_id,
                promotion_apply_status, null, nick_name, birthdate);
    }

}
