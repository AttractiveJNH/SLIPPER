package com.example.Slipper.dto.MeetingBoard;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.SswTestEntity.Entrepreneurs;
import com.example.Slipper.entity.SswTestEntity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MeetingWriteForm {

    private int meet_category;

    private String meet_title;

    private LocalDateTime meet_write_date;

    private int meet_max_participants;


    private LocalDate meet_apply_end_date;

    private LocalDate meet_date;

    private String meet_field;

    private String meet_content;



    //일반 유저 모임 게시판 글작성
    public MeetingBoard userMeetEntity(Users user_num, String nick_name){
        return new MeetingBoard(null, user_num, null, meet_category, meet_title,
                meet_write_date, 0, 1, meet_max_participants,
                meet_apply_end_date, meet_date, meet_field, meet_content, nick_name);
    }

    //사업자 유저 모임 게시판 글작성
    public MeetingBoard entrepreneurMeetEntity(Entrepreneurs entrepre_num, String nick_name){
        return new MeetingBoard(null, null, entrepre_num, meet_category, meet_title,
                meet_write_date, 0, 1, meet_max_participants,
                meet_apply_end_date, meet_date, meet_field, meet_content, nick_name);
    }


}
