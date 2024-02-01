package com.example.Slipper.controller;

import com.example.Slipper.dto.UserDto;
import com.example.Slipper.repository.userAndEntreRepositories.UserRepository;
import com.example.Slipper.service.loginAndJoinServices.JoinService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.swing.*;


// 유저 컨트롤러
@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {


    private final JoinService joinService;

    private final UserRepository userRepository;

    private final UserService userService;

    // 로그인 페이지 맵핑


    // 유저 회원 가입
    @GetMapping("/user_join")
    public String userJoinP(Model model){

        model.addAttribute("userDto", new UserDto());
        return "signup/user_signup";
    }
    @PostMapping("/user_join")
    public String userJoinProc(@Valid @ModelAttribute UserDto userDto, BindingResult bindingResult, Model model){

        // loginId 중복 체크
        if(userService.checkLoginIdDuplicate(userDto.getId())) {
            bindingResult.addError(new FieldError("userDto", "userId", "로그인 아이디가 중복됩니다."));
        }
        // 닉네임 중복 체크
        if(userService.checkNicknameDuplicate(userDto.getUserNickName())) {
            bindingResult.addError(new FieldError("userDto", "userNickName", "닉네임이 중복됩니다."));
        }
        // password와 passwordCheck가 같은지 체크
        if(!userDto.getPassword().equals(userDto.getPasswordCheck())) {
            bindingResult.addError(new FieldError("userDto", "userPasswordCheck", "바밀번호가 일치하지 않습니다."));
        }

        if(bindingResult.hasErrors()) {
            return "signup/user_signup";
        }





         log.info(userDto.getId());

        joinService.userJoinProcess(userDto);
        return "redirect:/login";
    }


}
