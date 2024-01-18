package com.example.Slipper.entity.MeetingBoardEntity;


import com.example.Slipper.entity.SswTestEntity.Entrepreneur;
import com.example.Slipper.entity.SswTestEntity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "meetingboard")
public class MeetingBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="meet_num")
    private Integer meet_num;

    @ManyToOne(targetEntity = Users.class)
    @JoinColumn(name = "user_num", referencedColumnName = "user_num")
    private Users user_num;

    @ManyToOne(targetEntity = Entrepreneur.class)
    @JoinColumn(name = "entrepre_num", referencedColumnName = "entrepre_num")
    private Entrepreneur entrepre_num;

    @Column(name ="meet_category")
    private int meet_category;

    @Column(name ="meet_title")
    private String meet_title;

    @CreatedDate
    @Column(name ="meet_write_date")
    private LocalDateTime meet_write_date;

    @Column(name ="meet_views")
    private int meet_views;

    @Column(name ="meet_now_particpants")
    private int meet_now_particpants;

    @Column(name ="meet_max_particpants")
    private int meet_max_particpants;

    @Column(name ="meet_apply_end_date")
    private LocalDate meet_apply_end_date;

    @Column(name ="meet_date")
    private LocalDate meet_date;

    @Column(name ="meet_field")
    private String meet_field;

    @Column(name ="meet_content", columnDefinition = "TEXT")
    private String meet_content;
}
