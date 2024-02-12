package com.example.Slipper.entity.generalEntity;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "generalboardcomment")
public class GeneralBoardComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generalcommentnum")
    private Integer generalCommentNum;

    @ManyToOne(targetEntity = GeneralBoard.class)
    @JoinColumn(name = "generalnum", referencedColumnName = "generalnum")
    private GeneralBoard generalNum;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_num", referencedColumnName = "user_num")
    private UserEntity user_num;

    @ManyToOne(targetEntity = EntreEntity.class)
    @JoinColumn(name = "entrepre_num", referencedColumnName = "entrepre_num")
    private EntreEntity entrepre_num;

    @Column(name ="nick_name")
    private String nick_name;

    @Column(name ="general_comment_content")
    private String general_comment_content;

    @CreatedDate
    @Column(name ="general_comment_date")
    private LocalDateTime general_comment_date;
}
