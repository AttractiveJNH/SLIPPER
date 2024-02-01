package com.example.Slipper.controller.MeetingBoard;


import com.example.Slipper.repository.MeetingBoardRepository;
import com.example.Slipper.repository.UsersRepository;
import com.example.Slipper.dto.MeetingBoard.MeetingWriteForm;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.SswTestEntity.Users;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.UUID;


@Controller
public class MeetingWriteController {
    @Autowired
    MeetingBoardRepository meetingBoardRepository;

    @Autowired
    UsersRepository usersRepository;


    //모임 게시판 글작성 페이지
    @GetMapping("/meeting/write")
    public String meetingWritePage(){
        return "meeting/meetingWrite";
    }

    //모임 게시판 글작성
    @PostMapping("/meeting/write/save")
    public String savePost(MeetingWriteForm meetingWriteForm){
        //임시로 유저정보를 가져옴 (이유: 로그인 기능 머지 미완료)
        Users userinfo = usersRepository.findById(1).orElse(null);
        String user_name = userinfo.getUser_nick_name();


        //일반 유저가 작성한 미팅게시판 게시글
        MeetingBoard user_meet_write = meetingWriteForm.userMeetEntity(userinfo, user_name);

        meetingBoardRepository.save(user_meet_write);


        return "redirect:/meeting/main";
    }

    //이미지 업로드
    @RequestMapping(value="smarteditorMultiImageUpload")
    public void smarteditorMultiImageUpload(HttpServletRequest request, HttpServletResponse response){
        try {
            //파일정보
            String sFileInfo = "";
            //파일명을 받는다 - 일반 원본파일명
            String sFilename = request.getHeader("file-name");
            //파일 확장자
            String sFilenameExt = sFilename.substring(sFilename.lastIndexOf(".")+1);
            //확장자를소문자로 변경
            sFilenameExt = sFilenameExt.toLowerCase();

            //이미지 검증 배열변수
            String[] allowFileArr = {"jpg","png","bmp","gif"};

            //확장자 체크
            int nCnt = 0;
            for(int i=0; i<allowFileArr.length; i++) {
                if(sFilenameExt.equals(allowFileArr[i])){
                    nCnt++;
                }
            }

            //이미지가 아니라면
            if(nCnt == 0) {
                PrintWriter print = response.getWriter();
                print.print("NOTALLOW_"+sFilename);
                print.flush();
                print.close();
            } else {
                //디렉토리 설정 및 업로드

                //파일경로
                String filePath = "//192.168.2.3/images/d/";

                File file = new File(filePath);

                if(!file.exists()) {
                    file.mkdirs();
                }

                String sRealFileNm = "";
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String today= formatter.format(new java.util.Date());
                sRealFileNm = today+ UUID.randomUUID().toString() + sFilename.substring(sFilename.lastIndexOf("."));
                String rlFileNm = filePath + sRealFileNm;

                ///////////////// 서버에 파일쓰기 /////////////////
                InputStream inputStream = request.getInputStream();
                OutputStream outputStream=new FileOutputStream(rlFileNm);
                int numRead;
                byte bytes[] = new byte[Integer.parseInt(request.getHeader("file-size"))];
                while((numRead = inputStream.read(bytes,0,bytes.length)) != -1){
                    outputStream.write(bytes,0,numRead);
                }
                if(inputStream != null) {
                    inputStream.close();
                }
                outputStream.flush();
                outputStream.close();

                ///////////////// 이미지 /////////////////
                // 정보 출력
                sFileInfo += "&bNewLine=true";
                // img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
                sFileInfo += "&sFileName="+ sFilename;
                sFileInfo += "&sFileURL="+"//192.168.2.3/images/d/"+sRealFileNm;
                PrintWriter printWriter = response.getWriter();
                printWriter.print(sFileInfo);
                printWriter.flush();
                printWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
