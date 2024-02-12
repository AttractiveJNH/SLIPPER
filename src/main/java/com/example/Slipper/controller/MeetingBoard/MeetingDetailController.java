package com.example.Slipper.controller.MeetingBoard;

import com.example.Slipper.dto.MeetingBoard.MeetingApplicationForm;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardApplication;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardComment;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.*;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import com.example.Slipper.service.meetingBoardServices.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MeetingDetailController {
    @Autowired
    private MeetingBoardRepository meetingBoardRepository;

    @Autowired
    private MeetingApplicationRepository meetingApplicationRepository;

    @Autowired
    private MeetingCommentRepository meetingCommentRepository;

    private final EntreService entreService;

    private final UserService userService;

    private final MeetingService meetingService;

    @Autowired
    public MeetingDetailController(MeetingService meetingService, UserService userService, EntreService entreService){
        this.meetingService = meetingService;
        this.userService = userService;
        this.entreService = entreService;
    }

    // 모임 게시판 상세 페이지
    @GetMapping("/meeting/detail/{meetNum}")
    public String meetingDetailPage(@PathVariable Integer meetNum, Model model, @PageableDefault Pageable pageable,
                                    @SessionAttribute(name = "id", required = false) String id){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);


        // 로그인 중인 유저 닉네임 및 생일 정보
        if (loginEntre != null){
            String nickName = loginEntre.getEntrepreNickName();
            LocalDate birthdate = loginEntre.getEntrepreBirthDate();

            model.addAttribute("nickName",nickName);
            model.addAttribute("birthdate",birthdate);
        } else if(loginUser != null){
            String nickName = loginUser.getUserNickName();
            LocalDate birthdate = loginUser.getUserBirthDate();

            model.addAttribute("nickName",nickName);
            model.addAttribute("birthdate",birthdate);
        } else {
            String nickName = null;
            LocalDate birthdate = null;

            model.addAttribute("nickName",nickName);
            model.addAttribute("birthdate",birthdate);
        }


        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        }

        // 해당 게시글 찾기
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);

        // 해당 게시글 작성자 정보와 현재 로그인 중인 유저가 같은지 검사 (1: 현재 로그인 중인 유저 = 작성자, 2: 현재 로그인 중인 유저 = 일반 유저)
        UserEntity userWriter = meetInfo.getUser_num();
        EntreEntity entreWriter = meetInfo.getEntrepre_num();
        if(entreWriter != null){
            // 작성자 = 사업자
            if(entreWriter == loginEntre){
                model.addAttribute("Writer", 1);
            } else {
                model.addAttribute("Writer", 2);
            }
        } else{
            // 작성자 = 일반 유저
            if(userWriter == loginUser){
                model.addAttribute("Writer", 1);
            } else {
                model.addAttribute("Writer", 2);
            }
        }

        // 해당 게시글 조회수 1증가
        meetInfo.setMeet_views(meetInfo.getMeet_views() + 1);
        meetingBoardRepository.save(meetInfo);

        // 댓글 페이징
        Page<MeetingBoardComment> commentList = meetingService.commentMeetingBoardList(meetNum, pageable);
        model.addAttribute("commentList", commentList);

        // 해당 게시글 정보
        model.addAttribute("meetInfo", meetInfo);

        return "meeting/meetingDetail";
    }


    // 모임 게시판 참가 신청 팝업
    @GetMapping("/meeting/apply/popup/{meetNum}")
    public String meetingApplyPage(@PathVariable Integer meetNum, Model model,
                                   @SessionAttribute(name = "id", required = false) String id){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        } else {
            return "redirect:/login";
        }

        // 게시글 정보
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);
        model.addAttribute("meetInfo",meetInfo);

        // 로그인 중인 유저가 해당 게시글 작성자가 맞는지 검사
        EntreEntity entreWriter = (meetInfo.getEntrepre_num() != null) ? meetInfo.getEntrepre_num() : null;
        UserEntity userWriter = (meetInfo.getUser_num() != null) ? meetInfo.getUser_num() : null;

        if(entreWriter != null){
            if(loginEntre != entreWriter){
                // 로그인 중인 유저가 해당 게시글 작성자가 아니면 로그인 페이지로 이동
                return "redirect:/login";
            } else if(loginEntre == entreWriter){
                // 해당 게시글 작성자 닉네임과 생일 정보
                String nickName = entreWriter.getEntrepreNickName();
                LocalDate birthdate = entreWriter.getEntrepreBirthDate();

                model.addAttribute("writerNickName",nickName);
                model.addAttribute("writerBirthdate",birthdate);
            }
        } else if(userWriter !=null){
            if(loginUser != userWriter){
                // 로그인 중인 유저가 해당 게시글 작성자가 아니면 로그인 페이지로 이동
                return "redirect:/login";
            }else if(loginUser == userWriter){
                // 해당 게시글 작성자 닉네임과 생일 정보
                String nickName = userWriter.getUserNickName();
                LocalDate birthdate = userWriter.getUserBirthDate();

                model.addAttribute("writerNickName",nickName);
                model.addAttribute("writerBirthdate",birthdate);
            }
        }

        // 참가 신청 대기자 정보 (meet_apply_status == 0)
        List<MeetingBoardApplication> applyWaitUser = meetingApplicationRepository.applyWaitUserList(meetNum);
        model.addAttribute("applyWaitUser",applyWaitUser);

        // 참가 수락한 유저 정보 (meet_apply_status == 1)
        List<MeetingBoardApplication> applyAcceptUser = meetingApplicationRepository.applyAcceptUserList(meetNum);
        model.addAttribute("applyAcceptUser",applyAcceptUser);

        // 참가 거절된 유저 정보 (meet_apply_status == 2)
        List<MeetingBoardApplication> applyRefuseUser = meetingApplicationRepository.applyRefuseUserList(meetNum);
        model.addAttribute("applyRefuseUser",applyRefuseUser);

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
    public String meetingApplication(@PathVariable Integer meetNum, MeetingApplicationForm form, RedirectAttributes redirectAttributes,
                                     @SessionAttribute(name = "id", required = false) String id, Model model){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);
        // 로그인한 유저의 키값
        Long loginEntreNum = (loginEntre != null) ? loginEntre.getEntrepreNum() : null;
        Long loginUserNum = (loginUser != null) ? loginUser.getUserNum() : null;

        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음) 여기에선 헤더가 따로 없기에 굳이 없어도되는 코드
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        }

        // 게시글 정보
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);

        // 로그인 중인 유형에 따라 참가 신청 처리
        if(loginEntre != null){
            // 사업자 로그인 중일때
            // 해당 유저가 해당 게시글 참가 신청 하였는지 체크
            MeetingBoardApplication entreApc = meetingApplicationRepository.entreApcCheck(loginEntreNum,meetInfo);
            if(entreApc != null){
                // 신청 정보가 있을때
                redirectAttributes.addFlashAttribute("msg","이미 신청하였습니다.");
            } else {
                // 신청 정보가 없을때
                // 해당 게시글 신청 인원이 마감 되었을 경우 체크
                if(isUnderMaxParticipants(meetInfo)){
                    // 참가 신청한 인원이 참가 최대 인원보다 작을때 신청 정보 저장
                    MeetingBoardApplication meetApplication = form.entrepreneurMeetApplication(loginEntre, meetInfo);
                    meetingApplicationRepository.save(meetApplication);
                    redirectAttributes.addFlashAttribute("msg","신청 완료되었습니다.");
                } else {
                    // 참가 신청한 인원이 참가 최대 인원과 같거나 클때 마감 문구
                    redirectAttributes.addFlashAttribute("msg","참가 인원이 마감되었습니다.");
                }
            }
        } else if(loginUser != null){
            // 일반 유저 로그인 중일때
            // 해당 유저가 해당 게시글 참가 신청 하였는지 체크
            MeetingBoardApplication userApc = meetingApplicationRepository.userApcCheck(loginUserNum,meetInfo);
            if(userApc != null){
                // 신청 정보가 있을때
                redirectAttributes.addFlashAttribute("msg","이미 신청하였습니다.");
            } else {
                // 신청 정보가 없을때
                // 해당 게시글 신청 인원이 마감 되었을 경우 체크
                if(isUnderMaxParticipants(meetInfo)){
                    // 참가 신청한 인원이 참가 최대 인원보다 작을때 신청 정보 저장
                    MeetingBoardApplication meetApplication = form.userMeetApplication(loginUser, meetInfo);
                    meetingApplicationRepository.save(meetApplication);
                    redirectAttributes.addFlashAttribute("msg","신청 완료되었습니다.");
                } else {
                    // 참가 신청한 인원이 참가 최대 인원과 같거나 클때 마감 문구
                    redirectAttributes.addFlashAttribute("msg","참가 인원이 마감되었습니다.");
                }
            }

        } else {
            // 로그인한 상태가 아닐때
            redirectAttributes.addFlashAttribute("msg","로그인 상태가 아닙니다.");
        }


        return "redirect:/meeting/detail/{meetNum}";
    }

    // (팝업창에서) 모임 게시판 참가 수락
    @PostMapping("/meetApplyAccept/{meetNum}/{entreNum}/{userNum}")
    public String acceptAction(@PathVariable Integer meetNum, @PathVariable Long entreNum,
                               @PathVariable Long userNum, RedirectAttributes redirectAttributes) {
        // 모임 게시판 정보
        MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);

        // 현재 참가 신청 인원 및 최대 참가 신청 인원
        int currentParticipants = meetInfo.getMeet_now_participants();
        int maxParticipants = meetInfo.getMeet_max_participants();

        // 참가 신청 인원과 최대 참가 신청 인원에 따른 결과
        if(currentParticipants < maxParticipants){
            // 참가 상태 업데이트
            if(entreNum != 0){
                // 사업자 유저 정보일때 참가 상태 대기에서 수락으로 업데이트
                MeetingBoardApplication applyAcceptUser = meetingApplicationRepository.findApplyEntre(meetNum, entreNum);
                applyAcceptUser.setMeet_apply_status(1);
                meetingApplicationRepository.save(applyAcceptUser);
                // 해당 게시판의 현재 참가 신청인원 +1 증가
                meetInfo.setMeet_now_participants(currentParticipants + 1);
                meetingBoardRepository.save(meetInfo);
            } else if(userNum != 0){
                // 일반 유저 정보일때 참가 상태 대기에서 수락으로 업데이트
                MeetingBoardApplication applyAcceptUser = meetingApplicationRepository.findApplyUser(meetNum, userNum);
                applyAcceptUser.setMeet_apply_status(1);
                meetingApplicationRepository.save(applyAcceptUser);
                // 해당 게시판의 현재 참가 신청인원 +1 증가
                meetInfo.setMeet_now_participants(currentParticipants + 1);
                meetingBoardRepository.save(meetInfo);
            } else {
                // 참자가 정보를 가져오지 못하였을때 (오류)
                redirectAttributes.addFlashAttribute("msg","(오류)참가자 정보를 가져오지 못하였습니다.");
            }

        } else if(currentParticipants == maxParticipants) {
            // 현재 참가 인원이 최대 참가 신청인원과 같을때
            redirectAttributes.addFlashAttribute("msg","현재 참가 인원이 최대 참가 인원과 같습니다.");
        } else {
            // 현재 참가 인원이 최대 참가 신청인원보다 많을때 (이부분은 사실상 에러. 실행되어선 안되는 경우의 수)
            redirectAttributes.addFlashAttribute("msg","현재 참가 인원이 최대 참가 인원을 초과했습니다.");
        }


        return "redirect:/meeting/apply/popup/{meetNum}";
    }


    // (팝업창에서) 모임 게시판 참가 거절
    @PostMapping("/meetApplyRefuse/{meetNum}/{entreNum}/{userNum}")
    public String refuseAction(@PathVariable Integer meetNum, @PathVariable Long entreNum,
                               @PathVariable Long userNum, RedirectAttributes redirectAttributes) {
        // 유저 유형별 참가 상태 업데이트
        if(entreNum != 0) {
            // 사업자 유저 정보일때 참가 상태 대기에서 거절로 업데이트
            MeetingBoardApplication applyRefuseUser = meetingApplicationRepository.findApplyEntre(meetNum, entreNum);
            applyRefuseUser.setMeet_apply_status(2);
            meetingApplicationRepository.save(applyRefuseUser);

        } else if(userNum != 0) {
            // 일반 유저 정보일때 참가 상태 대기에서 거절로 업데이트
            MeetingBoardApplication applyRefuseUser = meetingApplicationRepository.findApplyUser(meetNum, userNum);
            applyRefuseUser.setMeet_apply_status(2);
            meetingApplicationRepository.save(applyRefuseUser);
        } else {
            // 참가자 정보를 가져오지 못하였을때 (오류)
            redirectAttributes.addFlashAttribute("msg","(오류)참가자 정보를 가져오지 못하였습니다.");
        }

        return "redirect:/meeting/apply/popup/{meetNum}";
    }

    // (팝업창에서) 모임 게시판 참가 취소
    @PostMapping("/meetApplyCancel/{meetNum}/{entreNum}/{userNum}")
    public String cancelAction(@PathVariable Integer meetNum, @PathVariable Long entreNum,
                               @PathVariable Long userNum, RedirectAttributes redirectAttributes) {
        // 유저 유형별 참가 상태 업데이트
        if(entreNum != 0) {
            // 사업자 유저 정보일때 참가 상태 거절에서 대기로 업데이트
            MeetingBoardApplication applyCancelUser = meetingApplicationRepository.findApplyEntre(meetNum, entreNum);
            applyCancelUser.setMeet_apply_status(0);
            meetingApplicationRepository.save(applyCancelUser);

            // 해당 게시판의 현재 참가 신청인원 -1 감소
            MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);
            int currentParticipants = meetInfo.getMeet_now_participants();
            meetInfo.setMeet_now_participants(currentParticipants - 1);
            meetingBoardRepository.save(meetInfo);

        } else if(userNum != 0) {
            // 일반 유저 정보일때 참가 상태 거절에서 대기로 업데이트
            MeetingBoardApplication applyCancelUser = meetingApplicationRepository.findApplyUser(meetNum, userNum);
            applyCancelUser.setMeet_apply_status(0);
            meetingApplicationRepository.save(applyCancelUser);

            // 해당 게시판의 현재 참가 신청인원 -1 감소
            MeetingBoard meetInfo = meetingBoardRepository.findById(meetNum).orElse(null);
            int currentParticipants = meetInfo.getMeet_now_participants();
            meetInfo.setMeet_now_participants(currentParticipants - 1);
            meetingBoardRepository.save(meetInfo);
        } else {
            // 참자가 정보를 가져오지 못하였을때 (오류)
            redirectAttributes.addFlashAttribute("msg","(오류)참가자 정보를 가져오지 못하였습니다.");
        }

        return "redirect:/meeting/apply/popup/{meetNum}";
    }

    // (팝업창에서) 모임 게시판 거절목록에서 취소
    @PostMapping("/meetApplyRefuseCancel/{meetNum}/{entreNum}/{userNum}")
    public String refuseCancelAction(@PathVariable Integer meetNum, @PathVariable Long entreNum,
                                     @PathVariable Long userNum,RedirectAttributes redirectAttributes){
        // 유저 유형별 참가 상태 업데이트
        if(entreNum != 0) {
            // 사업가 유저 정보일때 참가 상태 거절에서 대기로 상태 업데이트
            MeetingBoardApplication applyCancelUser = meetingApplicationRepository.findApplyEntre(meetNum, entreNum);
            applyCancelUser.setMeet_apply_status(0);
            meetingApplicationRepository.save(applyCancelUser);

        } else if(userNum != 0) {
            // 일반 유저 정보일때 참가 상태 거절에서 대기로 상태 업데이트
            MeetingBoardApplication applyCancelUser = meetingApplicationRepository.findApplyUser(meetNum, userNum);
            applyCancelUser.setMeet_apply_status(0);
            meetingApplicationRepository.save(applyCancelUser);
        } else {
            // 참자가 정보를 가져오지 못하였을때 (오류)
            redirectAttributes.addFlashAttribute("msg","(오류)참가자 정보를 가져오지 못하였습니다.");
        }

        return "redirect:/meeting/apply/popup/{meetNum}";
    }

    // 게시판 삭제
    @PostMapping("/meeting/delete/{meetNum}")
    public String meetDelete(@PathVariable Integer meetNum, RedirectAttributes redirectAttributes,
                             @SessionAttribute(name = "id", required = false) String id, Model model){
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
                // 로그인 중인 유저와 게시글 작정자가 같을때 게시글 삭제
                // FK로 묶여있는 참가 신청테이블의 해당 게시글에 대한 데이터 삭제
                meetingApplicationRepository.deleteMeetApc(meetNum);
                // FK로 묶여있는 댓글 테이블의 해당 게시글에 대한 데이터 삭제
                meetingCommentRepository.meetCommentDelete(meetNum);

                // 해당 게시글 삭제
                meetingBoardRepository.deleteById(meetNum);
                redirectAttributes.addFlashAttribute("msg","삭제가 완료되었습니다.");
            } else{
                // 실행될 수 없는 경우의 수 (이 코드가 실행된다면 뷰에서 로그인 유형별로 보여져야하는 수정/삭제 버튼 오류)
                redirectAttributes.addFlashAttribute("msg","오류!");
                return "redirect:/meeting/detail/{meetNum}";
            }

        } else if(loginUser != null){
            // 일반 유저 로그인 중일때
            if(loginUserNum == userWriterNum){
                // 로그인 중인 유저와 게시글 작성자가 같을때 게시글 삭제
                // FK로 묶여있는 참가 신청테이블의 해당 게시글에 대한 데이터 삭제
                meetingApplicationRepository.deleteMeetApc(meetNum);
                // FK로 묶여있는 댓글 테이블의 해당 게시글에 대한 데이터 삭제
                meetingCommentRepository.meetCommentDelete(meetNum);

                // 해당 게시글 삭제
                meetingBoardRepository.deleteById(meetNum);
                redirectAttributes.addFlashAttribute("msg","삭제 완료되었습니다.");
            } else{
                // 실행될 수 없는 경우의 수 (이 코드가 실행된다면 뷰에서 로그인 유형별로 보여져야하는 수정/삭제 버튼 오류)
                redirectAttributes.addFlashAttribute("msg","오류!");
                return "redirect:/meeting/detail/{meetNum}";
            }
        } else {
            // 로그인 중이 아닐때
            redirectAttributes.addFlashAttribute("msg","로그인 세션이 만료되었습니다.");
            return "redirect:/meeting/detail/{meetNum}";
        }


        return "redirect:/meeting/main";
    }



}
