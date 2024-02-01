package com.example.Slipper.dto.MeetingBoard;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardApplication;
import com.example.Slipper.entity.SswTestEntity.Entrepreneurs;
import com.example.Slipper.entity.SswTestEntity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MeetingApplicationForm {

    private Integer meet_apply_status;

    private String nick_name;

    private LocalDate birthdate;

    // 일반 유저 모임 게시판 신청
    public MeetingBoardApplication userMeetApplication(Users user_num, MeetingBoard meet_num){
        return new MeetingBoardApplication(null, user_num, null, meet_num,
                meet_apply_status, null, nick_name, birthdate);
    }

    // 사업자 유저 모임 게시판 신청
    public MeetingBoardApplication entrepreneurMeetApplication(Entrepreneurs entrepre_num, MeetingBoard meet_num){
        return new MeetingBoardApplication(null, null, entrepre_num, meet_num,
                meet_apply_status, null, nick_name, birthdate);
    }
}
