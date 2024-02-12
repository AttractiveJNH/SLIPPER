package com.example.Slipper.dto.MeetingBoard;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardComment;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MeetingCommentForm {
    private String meet_comment_content;

    private LocalDateTime meet_comment_date;

    // 일반 유저 댓글 작성
    public MeetingBoardComment userMeetComment(MeetingBoard meetNum, UserEntity user_num, String nick_name){
        return new MeetingBoardComment(null, meetNum, user_num, null, nick_name,
                meet_comment_content, meet_comment_date);
    }

    // 사업가 유저 댓글 작성
    public MeetingBoardComment entrepreneurMeetComment(MeetingBoard meetNum, EntreEntity entrepre_num, String nick_name){
        return new MeetingBoardComment(null, meetNum, null, entrepre_num, nick_name,
                meet_comment_content, meet_comment_date);
    }
}
