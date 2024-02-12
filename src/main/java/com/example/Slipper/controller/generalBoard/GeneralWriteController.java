package com.example.Slipper.controller.generalBoard;

import com.example.Slipper.dto.MeetingBoard.MeetingWriteForm;
import com.example.Slipper.dto.generalDto.GeneralWriteForm;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.generalEntity.GeneralBoard;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.generalRepository.GeneralBoardRepository;
import com.example.Slipper.service.generalBoardServices.GeneralService;
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
public class GeneralWriteController {
    @Autowired
    private GeneralBoardRepository generalBoardRepository;

    private final UserService userService;

    private final EntreService entreService;

    @Autowired
    public GeneralWriteController(UserService userService, EntreService entreService){
        this.userService = userService;
        this.entreService = entreService;
    }

    //일반 게시판 글작성 페이지
    @GetMapping("/general/write")
    public String generalWritePage(@SessionAttribute(name = "id", required = false) String id, Model model){
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

        return "general/generalWrite";
    }

    //모임 게시판 글작성
    @PostMapping("/general/write/save")
    public String savePost(GeneralWriteForm form, @SessionAttribute(name = "id", required = false) String id,
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
            GeneralBoard entre_general_write = form.entrepreneurGeneralEntity(loginEntre, entre_nick_name);
            generalBoardRepository.save(entre_general_write);
            redirectAttributes.addFlashAttribute("msg","게시글 작성이 완료되었습니다.");
            return "redirect:/general/main";
        } else if (loginUser != null){
            // 일반 유저 로그인 중일때 글작성
            String user_nick_name = loginUser.getUserNickName();
            GeneralBoard user_general_write = form.userGeneralEntity(loginUser, user_nick_name);
            generalBoardRepository.save(user_general_write);
            redirectAttributes.addFlashAttribute("msg","게시글 작성이 완료되었습니다.");
            return "redirect:/general/main";
        } else{
            // 로그인 중이 아닐때 로그인 페이지로 이동
            redirectAttributes.addFlashAttribute("msg","로그인 세션이 만료되었습니다.");
            return "redirect:/login";
        }
    }

    // 게시판 수정 페이지
    @GetMapping("/general/modify/{generalNum}")
    public String generalModify(@PathVariable Integer generalNum, @SessionAttribute(name = "id", required = false) String id,
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
        GeneralBoard generalInfo = generalBoardRepository.findById(generalNum).orElse(null);
        model.addAttribute("generalInfo", generalInfo);
        return "general/generalModify";
    }

    // 게시글 수정
    @PostMapping("/general/modify/save/{generalNum}")
    public String generalModifyPost(@PathVariable Integer generalNum, GeneralWriteForm form,
                             @SessionAttribute(name = "id", required = false) String id, Model model, RedirectAttributes redirectAttributes){

        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 로그인한 유저의 키값
        Long loginEntreNum = (loginEntre != null) ? loginEntre.getEntrepreNum() : null;
        Long loginUserNum = (loginUser != null) ? loginUser.getUserNum() : null;

        // 해당 게시글 찾기
        GeneralBoard GeneralInfo = generalBoardRepository.findById(generalNum).orElse(null);

        // 해당 게시글 작성자 키값 찾기
        Long entreWriterNum = (generalBoardRepository.generalEntreWriterFind(generalNum) != null) ? generalBoardRepository.generalEntreWriterFind(generalNum) : null;
        Long userWriterNum = (generalBoardRepository.generalUserWriterFind(generalNum) != null) ? generalBoardRepository.generalUserWriterFind(generalNum) : null;

        // 로그인별 처리
        if(loginEntre != null){
            // 사업자 유저 로그인 중일때
            if(loginEntreNum == entreWriterNum){
                // 로그인 중인 유저와 게시글 작정자가 같을때 게시글 수정
                // 해당 게시글 수정
                GeneralInfo.setGeneral_category(form.getGeneral_category());
                GeneralInfo.setGeneral_title(form.getGeneral_title());
                GeneralInfo.setGeneral_content(form.getGeneral_content());

                generalBoardRepository.save(GeneralInfo);
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
                GeneralInfo.setGeneral_category(form.getGeneral_category());
                GeneralInfo.setGeneral_title(form.getGeneral_title());
                GeneralInfo.setGeneral_content(form.getGeneral_content());

                generalBoardRepository.save(GeneralInfo);
                redirectAttributes.addFlashAttribute("msg","수정이 완료되었습니다.");
            } else{
                // 실행될 수 없는 경우의 수 (이 코드가 실행된다면 뷰에서 로그인 유형별로 보여져야하는 수정/삭제 버튼 오류)
                redirectAttributes.addFlashAttribute("msg","오류!");
            }
        } else {
            // 로그인 중이 아닐때
            redirectAttributes.addFlashAttribute("msg","로그인 세션이 만료되었습니다.");
        }

        return "redirect:/general/detail/{generalNum}";
    }


}
