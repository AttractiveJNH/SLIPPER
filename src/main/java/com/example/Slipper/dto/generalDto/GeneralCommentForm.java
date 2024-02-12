package com.example.Slipper.dto.generalDto;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardComment;
import com.example.Slipper.entity.generalEntity.GeneralBoard;
import com.example.Slipper.entity.generalEntity.GeneralBoardComment;
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
public class GeneralCommentForm {
    private String general_comment_content;

    private LocalDateTime general_comment_date;

    // 일반 유저 댓글 작성
    public GeneralBoardComment userGeneralComment(GeneralBoard generalCommentNum, UserEntity user_num, String nick_name){
        return new GeneralBoardComment(null, generalCommentNum, user_num, null, nick_name,
                general_comment_content, general_comment_date);
    }

    // 사업가 유저 댓글 작성
    public GeneralBoardComment entrepreneurGeneralComment(GeneralBoard generalCommentNum, EntreEntity entrepre_num, String nick_name){
        return new GeneralBoardComment(null, generalCommentNum, null, entrepre_num, nick_name,
                general_comment_content, general_comment_date);
    }
}
