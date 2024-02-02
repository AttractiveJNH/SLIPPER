package com.example.Slipper.entity.promotionEntity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "promotionBoardImage")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromotionBoardImage {

    @Id
    @Column(name = "promo_brd_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int promoBrdImgId; // 번호

    @Column(name = "promo_brd_post_id")
    private int promoBrdPostId; // 홍보 게시판 번호

    private String promoBrdImgAddress; // 이미지 주소
}
