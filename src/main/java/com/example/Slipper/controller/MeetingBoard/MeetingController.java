package com.example.Slipper.controller.MeetingBoard;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MeetingController {
    //모임게시판 목록 페이지
    @GetMapping("/meeting/main")
    public String meetingMainPage(){
        return "meeting/meetingMain";
    }

    //모임게시판 글작성 페이지
    @GetMapping("/meeting/write")
    public String meetingWritePage(){
        return "meeting/meetingWrite";
    }


}
