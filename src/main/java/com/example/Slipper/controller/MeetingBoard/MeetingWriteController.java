package com.example.Slipper.controller.MeetingBoard;


import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.MeetingBoardRepository;
import com.example.Slipper.dto.MeetingBoard.MeetingWriteForm;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.repository.userAndEntreRepositories.EntreRepository;
import com.example.Slipper.repository.userAndEntreRepositories.UserRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.UUID;


@Controller
public class MeetingWriteController {
    @Autowired
    MeetingBoardRepository meetingBoardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntreRepository entreRepository;

    private final EntreService entreService;

    private final UserService userService;

    @Autowired
    public MeetingWriteController(UserService userService, EntreService entreService){
        this.userService = userService;
        this.entreService = entreService;
    }


    //모임 게시판 글작성 페이지
    @GetMapping("/meeting/write")
    public String meetingWritePage(@SessionAttribute(name = "id", required = false) String id, Model model){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        } else {
            // 로그인 중이 아닐때 로그인 페이지로 이동
            return "redirect:/login";
        }

        return "meeting/meetingWrite";
    }

    //모임 게시판 글작성
    @PostMapping("/meeting/write/save")
    public String savePost(MeetingWriteForm meetingWriteForm, @SessionAttribute(name = "id", required = false) String id,
                           Model model, RedirectAttributes redirectAttributes){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        }

        // 사업자 로그인 중일때 글작성
        if (loginEntre != null){
            // 사업자 유저 로그인 중일때 글작성
            String entre_nick_name = loginEntre.getEntrepreNickName();
            MeetingBoard entre_meet_write = meetingWriteForm.entrepreneurMeetEntity(loginEntre, entre_nick_name);
            meetingBoardRepository.save(entre_meet_write);
            redirectAttributes.addFlashAttribute("msg","게시글 작성이 완료되었습니다.");
            return "redirect:/meeting/main";
        } else if (loginUser != null){
            // 일반 유저 로그인 중일때 글작성
            String user_nick_name = loginUser.getUserNickName();
            MeetingBoard user_meet_write = meetingWriteForm.userMeetEntity(loginUser, user_nick_name);
            meetingBoardRepository.save(user_meet_write);
            redirectAttributes.addFlashAttribute("msg","게시글 작성이 완료되었습니다.");
            return "redirect:/meeting/main";
        } else{
            // 로그인 중이 아닐때 로그인 페이지로 이동
            redirectAttributes.addFlashAttribute("msg","로그인 세션이 만료되었습니다.");
            return "redirect:/login";
        }
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

    // 게시판 수정 페이지
    @GetMapping("/meeting/modify/{meetNum}")
    public String meetModify(@PathVariable Integer meetNum, @SessionAttribute(name = "id", required = false) String id,
                             Model model){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        } else {
            // 로그인 중이 아닐때 로그인 페이지로 이동
            return "redirect:/login";
        }

        // 해당 게시글 정보
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);
        model.addAttribute("meetInfo", meetInfo);
        return "meeting/meetingModify";
    }

    // 게시글 수정
    @PostMapping("/meeting/modify/save/{meetNum}")
    public String modifyPost(@PathVariable Integer meetNum, MeetingWriteForm form,
                             @SessionAttribute(name = "id", required = false) String id, Model model, RedirectAttributes redirectAttributes){

        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 로그인한 유저의 키값
        Long loginEntreNum = (loginEntre != null) ? loginEntre.getEntrepreNum() : null;
        Long loginUserNum = (loginUser != null) ? loginUser.getUserNum() : null;

        // 해당 게시글 찾기
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);

        // 해당 게시글 작성자 키값 찾기
        Long entreWriterNum = (meetingBoardRepository.entreWriterFind(meetNum) != null) ? meetingBoardRepository.entreWriterFind(meetNum) : null;
        Long userWriterNum = (meetingBoardRepository.userWriterFind(meetNum) != null) ? meetingBoardRepository.userWriterFind(meetNum) : null;

        // 로그인별 처리
        if(loginEntre != null){
            // 사업자 유저 로그인 중일때
            if(loginEntreNum == entreWriterNum){
                // 로그인 중인 유저와 게시글 작정자가 같을때 게시글 수정
                // 해당 게시글 수정
                meetInfo.setMeet_field(form.getMeet_field());
                meetInfo.setMeet_category(form.getMeet_category());
                meetInfo.setMeet_title(form.getMeet_title());
                meetInfo.setMeet_date(form.getMeet_date());
                meetInfo.setMeet_apply_end_date(form.getMeet_apply_end_date());
                meetInfo.setMeet_max_participants(form.getMeet_max_participants());
                meetInfo.setMeet_content(form.getMeet_content());

                meetingBoardRepository.save(meetInfo);
                redirectAttributes.addFlashAttribute("msg","수정이 완료되었습니다.");
            } else{
                // 실행될 수 없는 경우의 수 (이 코드가 실행된다면 뷰에서 로그인 유형별로 보여져야하는 수정/삭제 버튼 오류)
                redirectAttributes.addFlashAttribute("msg","오류!");
            }

        } else if(loginUser != null){
            // 일반 유저 로그인 중일때
            if(loginUserNum == userWriterNum){
                // 로그인 중인 유저와 게시글 작성자가 같을때 게시글 수정
                // 해당 게시글 수정
                meetInfo.setMeet_field(form.getMeet_field());
                meetInfo.setMeet_category(form.getMeet_category());
                meetInfo.setMeet_title(form.getMeet_title());
                meetInfo.setMeet_date(form.getMeet_date());
                meetInfo.setMeet_apply_end_date(form.getMeet_apply_end_date());
                meetInfo.setMeet_max_participants(form.getMeet_max_participants());
                meetInfo.setMeet_content(form.getMeet_content());

                meetingBoardRepository.save(meetInfo);
                redirectAttributes.addFlashAttribute("msg","수정이 완료되었습니다.");
            } else{
                // 실행될 수 없는 경우의 수 (이 코드가 실행된다면 뷰에서 로그인 유형별로 보여져야하는 수정/삭제 버튼 오류)
                redirectAttributes.addFlashAttribute("msg","오류!");
            }
        } else {
            // 로그인 중이 아닐때
            redirectAttributes.addFlashAttribute("msg","로그인 세션이 만료되었습니다.");
        }

        return "redirect:/meeting/detail/{meetNum}";
    }
}
