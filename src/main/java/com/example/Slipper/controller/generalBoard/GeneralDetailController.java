package com.example.Slipper.controller.generalBoard;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardComment;
import com.example.Slipper.entity.generalEntity.GeneralBoard;
import com.example.Slipper.entity.generalEntity.GeneralBoardComment;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.generalRepository.GeneralBoardRepository;
import com.example.Slipper.repository.generalRepository.GeneralCommentRepository;
import com.example.Slipper.service.generalBoardServices.GeneralService;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
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

@Controller
public class GeneralDetailController {
    @Autowired
    private GeneralBoardRepository generalBoardRepository;

    @Autowired
    private GeneralCommentRepository generalCommentRepository;

    private final UserService userService;

    private final EntreService entreService;

    private final GeneralService generalService;

    @Autowired
    public GeneralDetailController(GeneralService generalService, UserService userService, EntreService entreService) {
        this.generalService = generalService;
        this.userService = userService;
        this.entreService = entreService;
    }

    // 일반 게시판 상세 페이지
    @GetMapping("/general/detail/{generalNum}")
    public String meetingDetailPage(@PathVariable Integer generalNum, Model model, @PageableDefault Pageable pageable,
                                    @SessionAttribute(name = "id", required = false) String id){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);


        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        }

        // 로그인 중인 유저 닉네임
        if (loginEntre != null){
            String nickName = loginEntre.getEntrepreNickName();

            model.addAttribute("nickName",nickName);
        } else if(loginUser != null){
            String nickName = loginUser.getUserNickName();

            model.addAttribute("nickName",nickName);
        } else {
            String nickName = null;

            model.addAttribute("nickName",nickName);
        }

        // 해당 게시글 찾기
        GeneralBoard generalInfo = generalBoardRepository.findById(generalNum).orElse(null);

        // 해당 게시글 작성자 정보와 현재 로그인 중인 유저가 같은지 검사 (1: 현재 로그인 중인 유저 = 작성자, 2: 현재 로그인 중인 유저 = 일반 유저)
        UserEntity userWriter = generalInfo.getUser_num();
        EntreEntity entreWriter = generalInfo.getEntrepre_num();
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
        generalInfo.setGeneral_views(generalInfo.getGeneral_views() + 1);
        generalBoardRepository.save(generalInfo);

        // 댓글 페이징
        Page<GeneralBoardComment> commentList = generalService.commentGeneralBoardList(generalNum, pageable);
        model.addAttribute("commentList", commentList);

        // 해당 게시글 정보
        model.addAttribute("generalInfo", generalInfo);

        return "general/generalDetail";
    }

    // 일반 게시판 삭제
    @PostMapping("/general/delete/{generalNum}")
    public String meetDelete(@PathVariable Integer generalNum, RedirectAttributes redirectAttributes,
                             @SessionAttribute(name = "id", required = false) String id, Model model){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 로그인한 유저의 키값
        Long loginEntreNum = (loginEntre != null) ? loginEntre.getEntrepreNum() : null;
        Long loginUserNum = (loginUser != null) ? loginUser.getUserNum() : null;

        // 해당 게시글 찾기
        GeneralBoard generalInfo = generalBoardRepository.findById(generalNum).orElse(null);

        // 해당 게시글 작성자 키값 찾기
        Long entreWriterNum = (generalBoardRepository.generalEntreWriter(generalNum) != null) ? generalBoardRepository.generalEntreWriter(generalNum) : null;
        Long userWriterNum = (generalBoardRepository.generalUserWriter(generalNum) != null) ? generalBoardRepository.generalUserWriter(generalNum) : null;


        // 로그인별 처리
        if(loginEntre != null){
            // 사업자 유저 로그인 중일때
            if(loginEntreNum == entreWriterNum){
                // 로그인 중인 유저와 게시글 작정자가 같을때 게시글 삭제
                // FK로 묶여있는 댓글 테이블의 해당 게시글에 대한 데이터 삭제
                generalCommentRepository.generalCommentDelete(generalNum);
                // 해당 게시글 삭제
                generalBoardRepository.deleteById(generalNum);
                redirectAttributes.addFlashAttribute("msg","삭제가 완료되었습니다.");
            } else{
                // 실행될 수 없는 경우의 수 (이 코드가 실행된다면 뷰에서 로그인 유형별로 보여져야하는 수정/삭제 버튼 오류)
                redirectAttributes.addFlashAttribute("msg","오류!");
                return "redirect:/general/detail/{generalNum}";
            }

        } else if(loginUser != null){
            // 일반 유저 로그인 중일때
            if(loginUserNum == userWriterNum){
                // 로그인 중인 유저와 게시글 작성자가 같을때 게시글 삭제
                // FK로 묶여있는 댓글 테이블의 해당 게시글에 대한 데이터 삭제
                generalCommentRepository.generalCommentDelete(generalNum);
                // 해당 게시글 삭제
                generalBoardRepository.deleteById(generalNum);
                redirectAttributes.addFlashAttribute("msg","삭제 완료되었습니다.");
            } else{
                // 실행될 수 없는 경우의 수 (이 코드가 실행된다면 뷰에서 로그인 유형별로 보여져야하는 수정/삭제 버튼 오류)
                redirectAttributes.addFlashAttribute("msg","오류!");
                return "redirect:/general/detail/{generalNum}";
            }
        } else {
            // 로그인 중이 아닐때
            redirectAttributes.addFlashAttribute("msg","로그인 세션이 만료되었습니다.");
            return "redirect:/general/detail/{generalNum}";
        }


        return "redirect:/general/main";
    }


}
