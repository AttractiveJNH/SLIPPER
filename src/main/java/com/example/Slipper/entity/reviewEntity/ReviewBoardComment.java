package com.example.Slipper.entity.reviewEntity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_board_comment")
@NoArgsConstructor
@Getter
@Setter
public class ReviewBoardComment {

    @Id
    @Column(name = "rev_brd_comnt_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int revBrdComntId; // 번호

    @Column(name = "revbrdpostid")
    private int revBrdPostId; // 홍보 게시판 번호



    @Column(name = "rev_brd_comnt_content")
    private String revBrdComntContent; // 내용

    @Column(name = "rev_brd_comnt_date")
    private LocalDateTime revBrdComntDate; // 작성일
    private String userNickName; // 유저닉네임
    private String entrepreNickName;
    private String userId;
    private String entrepreId;

    public ReviewBoardComment(int revBrdPostId, String revBrdComntContent, LocalDateTime revBrdComntDate, String userNickName, String entrepreNickName, String userId, String entrepreId) {
        this.revBrdPostId = revBrdPostId;
        this.revBrdComntContent = revBrdComntContent;
        this.revBrdComntDate = revBrdComntDate;
        this.userNickName = userNickName;
        this.entrepreNickName = entrepreNickName;
        this.userId = userId;
        this.entrepreId = entrepreId;
    }
}
