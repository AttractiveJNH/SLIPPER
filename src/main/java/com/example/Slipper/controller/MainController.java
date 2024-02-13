package com.example.Slipper.controller;

import com.example.Slipper.dto.EntreDto;
import com.example.Slipper.dto.LoginRequest;
import com.example.Slipper.dto.UserDto;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.userAndEntreRepositories.EntreRepository;
import com.example.Slipper.repository.userAndEntreRepositories.UserRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final UserService userService;

    private final EntreService entreService;

    private final UserRepository userRepository;

    private final EntreRepository entreRepository;


    @GetMapping("/main")
    public String mainPage(Model model, @SessionAttribute(name = "id", required = false) String id) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        if (loginEntre != null) {

            model.addAttribute("id", true);
            model.addAttribute("nickName", loginEntre.getEntrepreNickName());

        } else if (loginUser != null) {
            model.addAttribute("id", true);
            model.addAttribute("nickName", loginUser.getUserNickName());

        } else {

            model.addAttribute("id", false);

        }


        return "main";
    }


    //로그인 Url
    @GetMapping("/login")
    public String loginP(Model model) {

        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    // 로그인 성공 시 맵핑
    @PostMapping("/login_proc")
    public String loginRequest(@ModelAttribute LoginRequest loginRequest, BindingResult bindingResult,
                               HttpServletRequest httpServletRequest, Model model,
                               @SessionAttribute(name = "id", required = false) String id) {

        model.addAttribute("loginType", "slipper");

        UserEntity user = userService.login(loginRequest);
        EntreEntity entre = entreService.login(loginRequest);


        // 로그인 아이디나 비밀번호가 틀린 경우 global error return
        if (user == null && entre == null) {
            bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
        }

        if (bindingResult.hasErrors()) {
            return "login";
        }
        // 로그인 성공 => 세션 생성

        // 세션을 생성하기 전에 기존의 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성

        // 세션에 userId를 넣어줌

        if (user != null) {
            session.setAttribute("id", user.getId());
            session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

            log.info(user.getId());

        } else if (entre != null) {
            session.setAttribute("id", entre.getId());
            session.setMaxInactiveInterval(1800); // Session이 30분동안 유지

        }

        if (user != null) {
            log.info("로그인 유저 아이디 : " + user.getId());
            log.info("유저 닉네임 : " + user.getUserNickName());
        } else {
            log.info("로그인 유저 아이디 : " + entre.getId());
            log.info("유저 닉네임 : " + entre.getEntrepreNickName());
        }


        return "redirect:/main";
    }

    //로그아웃 Url
    @GetMapping("/logOut")
    public String logout(HttpServletRequest request, Model model) {
        model.addAttribute("loginType", "slipper");

        HttpSession session = request.getSession(false);  // Session이 없으면 null return
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/main";
    }
//
//    //아이디 찾기 Url
//    @GetMapping("/findId")
//    public String findId(HttpServletRequest request, Model model, EntreDto entreDto,
//                         UserDto userDto, @RequestParam LocalDate birthDate, @RequestParam String name){
//
//        try {
//            entreDto.setEntrepreBirthDate(birthDate);
//            entreDto.setEntrepreName(name);
//            EntreDto
//        }
//
//        return "";
//    }

}
