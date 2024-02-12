package com.example.Slipper.entity.MeetingBoardEntity;

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
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "meetingboardcomment")
public class MeetingBoardComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meetcommentnum")
    private Integer meetCommentNum;

    @ManyToOne(targetEntity = MeetingBoard.class)
    @JoinColumn(name = "meetnum", referencedColumnName = "meetnum")
    private MeetingBoard meetNum;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "user_num", referencedColumnName = "user_num")
    private UserEntity user_num;

    @ManyToOne(targetEntity = EntreEntity.class)
    @JoinColumn(name = "entrepre_num", referencedColumnName = "entrepre_num")
    private EntreEntity entrepre_num;

    @Column(name ="nick_name")
    private String nick_name;

    @Column(name ="meet_comment_content")
    private String meet_comment_content;

    @CreatedDate
    @Column(name ="meet_comment_date")
    private LocalDateTime meet_comment_date;

    public void setDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        this.meet_comment_date = LocalDateTime.parse(formattedDateTime, formatter);
    }
}
