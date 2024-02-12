package com.example.Slipper.entity.promotionEntity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "promotion_board_comment")
@NoArgsConstructor

@Getter
@Setter
public class PromotionBoardComment {

    @Id
    @Column(name = "promo_brd_comnt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int promoBrdComntId; // 번호

    @Column(name = "promobrdpostid")
    private int promoBrdPostId; // 홍보 게시판 번호



    @Column(name = "promo_brd_comnt_content")
    private String promoBrdComntContent; // 내용

    @Column(name = "promo_brd_comnt_date")
    @DateTimeFormat()
    private LocalDateTime  promoBrdComntDate; // 작성일
    private String userNickName; // 유저닉네임
    private String entrepreNickName;
    private String userId;
    private String entrepreId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_num", insertable = false, updatable = false)
//    private com.example.Slipper.entity.UserEntity user;



    public PromotionBoardComment(int promoBrdPostId, String promoBrdComntContent, LocalDateTime promoBrdComntDate, String userNickName, String entrepreNickName, String userId, String entrepreId) {
        this.promoBrdPostId = promoBrdPostId;
        this.promoBrdComntContent = promoBrdComntContent;
        this.promoBrdComntDate = promoBrdComntDate;
        this.userNickName = userNickName;
        this.entrepreNickName = entrepreNickName;
        this.userId = userId;
        this.entrepreId = entrepreId;
    }
}
