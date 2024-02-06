package com.example.Slipper.controller.MeetingBoard;

import com.example.Slipper.dto.MeetingBoard.MeetingApplicationForm;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardApplication;
import com.example.Slipper.entity.SswTestEntity.Entrepreneurs;
import com.example.Slipper.entity.SswTestEntity.Users;
import com.example.Slipper.repository.EntrepreneurRepository;
import com.example.Slipper.repository.MeetingApplicationRepository;
import com.example.Slipper.repository.MeetingBoardRepository;
import com.example.Slipper.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MeetingDetailController {
    @Autowired
    private MeetingBoardRepository meetingBoardRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EntrepreneurRepository entrepreneurRepository;

    @Autowired
    private MeetingApplicationRepository meetingApplicationRepository;

    // 모임 게시판 상세 페이지
    @GetMapping("/meeting/detail/{meetNum}")
    public String meetingDetailPage(@PathVariable Integer meetNum, Model model){
        //현재 로그인 중인 아이디 (로그인 기능 머지 미완료 -> 정적 테스트)
        Users userInfo = usersRepository.findById(1).orElse(null);
        Entrepreneurs entrepreneurInfo = entrepreneurRepository.findById(9999).orElse(null);

        // 해당 게시글 찾기
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);

        // 해당 게시글 작성자 정보와 현재 로그인 중인 유저가 같은지 검사 (1: 현재 로그인 중인 유저 = 작성자, 2: 현재 로그인 중인 유저 = 일반 유저)
        Users userWriter = meetInfo.getUser_num();
        Entrepreneurs entrepreneurWriter = meetInfo.getEntrepre_num();
        if(entrepreneurWriter != null){
            if(entrepreneurWriter == entrepreneurInfo){
                model.addAttribute("Writer", 1);
            } else {
                model.addAttribute("Writer", 2);
            }

        } else{
            if(userWriter == userInfo){
                model.addAttribute("Writer", 1);
            } else {
                model.addAttribute("Writer", 2);
            }
        }


        // 해당 게시글 조회수 1증가
        meetInfo.setMeet_views(meetInfo.getMeet_views() + 1);
        meetingBoardRepository.save(meetInfo);

        // 해당 게시글 정보
        model.addAttribute("meetInfo", meetInfo);
        // 해당 게시글을 보고있는 유저 정보
        model.addAttribute("userInfo", userInfo);


        return "meeting/meetingDetail";
    }


    // 모임 게시판 참가 신청 팝업
    @GetMapping("/meeting/apply/popup/{meetNum}")
    public String meetingApplyPage(@PathVariable Integer meetNum, Model model){
        // 게시글 정보
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);
        model.addAttribute("meetInfo",meetInfo);

        // 참가 신청 대기자 정보 (meet_apply_status == 0)
        List<MeetingBoardApplication> applyWaitUser = meetingApplicationRepository.applyWaitUserList(meetNum);
        model.addAttribute("applyWaitUser",applyWaitUser);

        // 참가 수락한 유저 정보 (meet_apply_status == 1)
        List<MeetingBoardApplication> applyAcceptUser = meetingApplicationRepository.applyAcceptUserList(meetNum);
        model.addAttribute("applyAcceptUser",applyAcceptUser);

        // 참가 거절된 유저 정보 (meet_apply_status == 2)
        List<MeetingBoardApplication> applyRefuseUser = meetingApplicationRepository.applyRefuseUserList(meetNum);
        model.addAttribute("applyRefuseUser",applyRefuseUser);

        // 게시글 작성자 정보
        Users meetWriteUser = meetInfo.getUser_num();
        model.addAttribute("meetWriteUser",meetWriteUser);

        return "meeting/meetingApply";
    }

    // 참가 신청한 인원이 참가 최대 인원보다 작을때 True 반환
    public boolean isUnderMaxParticipants(MeetingBoard meetInfo) {
        int nowParticipants = meetInfo.getMeet_now_participants();
        int maxParticipants = meetInfo.getMeet_max_participants();
        return nowParticipants < maxParticipants;
    }

    // (세부페이지에서) 모임 게시판 참가 신청
    @PostMapping("/meeting/application/{meetNum}")
    public String meetingApplication(@PathVariable Integer meetNum, MeetingApplicationForm form, RedirectAttributes redirectAttributes){
        //현재 로그인 중인 아이디 (로그인 기능 머지 미완료 -> 정적 테스트)
        Users userInfo = usersRepository.findById(1).orElse(null);
        Entrepreneurs entrepreneurInfo = entrepreneurRepository.findById(9999).orElse(null);

        // 게시글 정보
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);


        // 일반 유저 중 해당 게시글 신청 하였는지 체크
        MeetingBoardApplication userApc = meetingApplicationRepository.userApcCheck(userInfo,meetInfo);
        if(userApc != null){
            redirectAttributes.addFlashAttribute("msg","이미 신청하였습니다.");
        } else {
            // 해당 게시글 신청 인원이 마감 되었을 경우 체크
            if(isUnderMaxParticipants(meetInfo)){
                MeetingBoardApplication meetApplication = form.userMeetApplication(userInfo, meetInfo);
                meetingApplicationRepository.save(meetApplication);
                redirectAttributes.addFlashAttribute("msg","신청 완료되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("msg","참가 인원이 마감되었습니다.");
            }
        }

        return "redirect:/meeting/detail/{meetNum}";
    }

    // (팝업창에서) 모임 게시판 참가 수락
    @PostMapping("/meetApplyAccept/{meetNum}/{userNum}")
    public String acceptAction(@PathVariable Integer meetNum, @PathVariable Integer userNum, RedirectAttributes redirectAttributes) {
        // 모임 게시판 정보
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);

        // 현재 참가 신청 인원 및 최대 참가 신청 인원
        int currentParticipants = meetInfo.getMeet_now_participants();
        int maxParticipants = meetInfo.getMeet_max_participants();

        // 참가 신청 인원과 최대 참가 신청 인원에 따른 결과
        if(currentParticipants < maxParticipants){
            // 참가 상태 업데이트
            MeetingBoardApplication applyAcceptUser = meetingApplicationRepository.findApplyUser(meetNum, userNum);
            applyAcceptUser.setMeet_apply_status(1);
            meetingApplicationRepository.save(applyAcceptUser);
            // 현재 참가 신청인원 +1
            meetInfo.setMeet_now_participants(currentParticipants + 1);
            meetingBoardRepository.save(meetInfo);
        } else if(currentParticipants == maxParticipants) {
            redirectAttributes.addFlashAttribute("msg","현재 참가 인원이 최대 참가 인원과 같습니다.");
        } else {
            // 이부분은 사실상 에러. 실행되어선 안되는 경우의 수.
            redirectAttributes.addFlashAttribute("msg","현재 참가 인원이 최대 참가 인원을 초과했습니다.");
        }


        return "redirect:/meeting/apply/popup/{meetNum}";
    }


    // (팝업창에서) 모임 게시판 참가 거절
    @PostMapping("/meetApplyRefuse/{meetNum}/{userNum}")
    public String refuseAction(@PathVariable Integer meetNum, @PathVariable Integer userNum) {
        // 거절 상태 업데이트
        MeetingBoardApplication applyRefuseUser = meetingApplicationRepository.findApplyUser(meetNum, userNum);
        applyRefuseUser.setMeet_apply_status(2);
        meetingApplicationRepository.save(applyRefuseUser);

        return "redirect:/meeting/apply/popup/{meetNum}";
    }

    // (팝업창에서) 모임 게시판 참가 취소
    @PostMapping("/meetApplyCancel/{meetNum}/{userNum}")
    public String cancelAction(@PathVariable Integer meetNum, @PathVariable Integer userNum) {
        // 거절 상태 업데이트
        MeetingBoardApplication applyCancelUser = meetingApplicationRepository.findApplyUser(meetNum, userNum);
        applyCancelUser.setMeet_apply_status(0);
        meetingApplicationRepository.save(applyCancelUser);

        // 현재 참가 인원 -1
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);
        int currentParticipants = meetInfo.getMeet_now_participants();
        meetInfo.setMeet_now_participants(currentParticipants - 1);
        meetingBoardRepository.save(meetInfo);

        return "redirect:/meeting/apply/popup/{meetNum}";
    }

    // (팝업창에서) 모임 게시판 거절목록에서 취소
    @PostMapping("/meetApplyRefuseCancel/{meetNum}/{userNum}")
    public String refuseCancelAction(@PathVariable Integer meetNum, @PathVariable Integer userNum) {
        // 거절 상태 업데이트
        MeetingBoardApplication applyCancelUser = meetingApplicationRepository.findApplyUser(meetNum, userNum);
        applyCancelUser.setMeet_apply_status(0);
        meetingApplicationRepository.save(applyCancelUser);


        return "redirect:/meeting/apply/popup/{meetNum}";
    }

}
