package com.example.Slipper.controller.MeetingBoard;


import com.example.Slipper.repository.MeetingBoardRepository;
import com.example.Slipper.repository.UsersRepository;
import com.example.Slipper.dto.MeetingBoard.MeetingWriteForm;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.SswTestEntity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MeetingWriteController {
    @Autowired
    MeetingBoardRepository meetingBoardRepository;

    @Autowired
    UsersRepository usersRepository;


    //모임게시판 글작성
    @PostMapping("/meeting/write/save")
    public String savePost(MeetingWriteForm meetingWriteForm){
        //임시로 유저정보를 가져옴 (이유: 로그인 기능 머지 미완료)
        Users userinfo = usersRepository.findById(2).orElse(null);


        //일반 유저가 작성한 미팅게시판 게시글
        MeetingBoard user_meet_write = meetingWriteForm.userMeetEntity(userinfo);

        meetingBoardRepository.save(user_meet_write);


        return "redirect:/meeting/main";
    }

}
