package com.example.Slipper.entity.promotionEntity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Entity
@Table(name = "promotion_board_comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PromotionBoardComment {

    @Id
    @Column(name = "promo_brd_comnt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int promoBrdComntId; // 번호

    @Column(name = "promo_brd_post_id")
    private int proBrdPostId; // 홍보 게시판 번호

    @Column(name = "user_num")
    private Long userNum; // 유저 등록 번호

    @Column(name = "entrepre_num")
    private int entrepreNum; // 사업자 번호

    private String promoBrdComntContent; // 내용
    private Date promoBrdComntDate; // 작성일
    private String userNickName; //

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num", insertable = false, updatable = false)
    private com.example.Slipper.entity.UserEntity user;
}
