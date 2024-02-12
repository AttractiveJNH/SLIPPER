package com.example.Slipper.entity.promotionEntity;

import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "promotionboardapplication")
public class PromotionBoardApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_apply_id")
    private Integer promotion_apply_id;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_num", referencedColumnName = "user_num")
    private UserEntity user_num;

    @ManyToOne(targetEntity = EntreEntity.class)
    @JoinColumn(name = "entrepre_num", referencedColumnName = "entrepre_num")
    private EntreEntity entrepre_num;

    @ManyToOne(targetEntity = PromotionBoard.class)
    @JoinColumn(name = "promobrdpostid", referencedColumnName = "promobrdpostid")
    private PromotionBoard promobrdpostid;

    // 신청 상태 =  0:신청  1:수락  2:거절
    @Column(name = "promotion_apply_status")
    private Integer promotion_apply_status;

    @Column(name ="promotion_apply_date")
    private LocalDate promotion_apply_date;

    @Column(name ="nick_name")
    private String nick_name;

    @Column(name ="birthdate")
    private LocalDate birthdate;

    // Repository에 최초로 저장될때 날짜를 저장함
    @PrePersist
    public void prePersist() {
        promotion_apply_date = LocalDate.now();
    }

}
