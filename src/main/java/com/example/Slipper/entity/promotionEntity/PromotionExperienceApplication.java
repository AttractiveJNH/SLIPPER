package com.example.Slipper.entity.promotionEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "promotionExperienceApplication")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromotionExperienceApplication {

    @Id
    @Column(name = "exp_apply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int expApplyId; // 체험 신청 번호

    @Column(name = "user_num")
    private int userNum; // 유저 등록 번호

    @Column(name = "promo_brd_post_id")
    private int promoBrdPostId; // 홍보 게시판 번호

    private int promoExpApplyStatus; // 신청 상황 수락 여부
}
