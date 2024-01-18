package com.example.Slipper.dto.MeetingBoard;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.SswTestEntity.Entrepreneur;
import com.example.Slipper.entity.SswTestEntity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MeetingWriteForm {

    private int meet_category;

    private String meet_title;

    private LocalDateTime meet_write_date;

    private int meet_max_particpants;


    private LocalDate meet_apply_end_date;

    private LocalDate meet_date;

    private String meet_field;

    private String meet_content;




    public MeetingBoard userMeetEntity(Users user_num){
        return new MeetingBoard(null, user_num, null, meet_category, meet_title,
                meet_write_date, 0, 1, meet_max_particpants,
                meet_apply_end_date, meet_date, meet_field, meet_content);
    }


    public MeetingBoard entrepreneurMeetEntity(Entrepreneur entrepre_num){
        return new MeetingBoard(null, null, entrepre_num, meet_category, meet_title,
                meet_write_date, 0, 1, meet_max_particpants,
                meet_apply_end_date, meet_date, meet_field, meet_content);
    }












    //일반 유저 게시글 작성
    public MeetingBoard usersEntity(Users user_num, String meet_content){
        return new MeetingBoard(null, user_num, null, meet_category, meet_title,
                meet_write_date, 0, 1, meet_max_particpants,
                meet_apply_end_date, meet_date, meet_field, meet_content);
    }


    //사업자 유저 게시글 작성
    public MeetingBoard entrepreneurEntity(Entrepreneur entrepre_num, String meet_content){
        return new MeetingBoard(null, null, entrepre_num, meet_category, meet_title,
                meet_write_date, 0, 1, meet_max_particpants,
                meet_apply_end_date, meet_date, meet_field, meet_content);
    }
}
