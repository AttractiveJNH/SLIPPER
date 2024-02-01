package com.example.Slipper.controller;


import com.example.Slipper.dto.EntreDto;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.JoinService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


// 유저 컨트롤러
@Controller
@Slf4j
public class EntreController {

    @Autowired
    private JoinService joinService;

    @Autowired
    private EntreService entreService;
//    // 사업자 회원 가입 맵핑
//    @GetMapping("/entrepre_join")
//    public String entreJoinP(){
//        return "signup/entre_signup";
//    }
//
//    //회원가입 성공 시 맵핑
//    @PostMapping("/entrepre_join")
//    public String entreJoinProc(EntreDto entreDto){
//
//
//
//        log.info(entreDto.getId());
//
//        joinService.entreJoinProcess(entreDto);
//        return "redirect:/login";
//    }


    // 로그인 페이지 맵핑


    // 유저 회원 가입
    @GetMapping("/entre_join")
    public String entreJoinP(Model model){

        model.addAttribute("entreDto", new EntreDto());
        return "signup/entre_signup";
    }


    @PostMapping("/entre_join")
    public String entreJoinProc(@Valid @ModelAttribute EntreDto entreDto, BindingResult bindingResult, Model model){

        // loginId 중복 체크
        if(entreService.checkLoginIdDuplicate(entreDto.getId())) {
            bindingResult.addError(new FieldError("entreDto", "id", "로그인 아이디가 중복됩니다."));
        }
        // 닉네임 중복 체크
        if(entreService.checkNicknameDuplicate(entreDto.getEntrepreNickName())) {
            bindingResult.addError(new FieldError("entreDto", "entrepreNickName", "닉네임이 중복됩니다."));
        }
        // password와 passwordCheck가 같은지 체크
        if(!entreDto.getPassword().equals(entreDto.getPasswordCheck())) {
            bindingResult.addError(new FieldError("entreDto", "passwordCheck", "바밀번호가 일치하지 않습니다."));
        }

        if(bindingResult.hasErrors()) {
            return "signup/entre_signup";
        }





        log.info(entreDto.getId());

        joinService.entreJoinProcess(entreDto);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        model.addAttribute("loginType", "Slipper");

        HttpSession session = request.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/main";
    }

}
