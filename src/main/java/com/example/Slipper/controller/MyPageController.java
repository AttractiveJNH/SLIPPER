package com.example.Slipper.controller;

import com.example.Slipper.dto.LoginRequest;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.userAndEntreRepositories.EntreRepository;
import com.example.Slipper.repository.userAndEntreRepositories.UserRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/myPage")
@Slf4j

public class MyPageController {

    private final UserService userService;

    private final EntreService entreService;

    private final UserRepository userRepository;

    private final EntreRepository entreRepository;

    public MyPageController(UserService userService, EntreService entreService,
                            UserRepository userRepository, EntreRepository entreRepository) {
        this.userService = userService;
        this.entreService = entreService;
        this.userRepository = userRepository;
        this.entreRepository = entreRepository;

    }

    @GetMapping("/main")
    public String myPageMain(Model model, @SessionAttribute(name = "id", required = false) String id) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);


        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)

        if (loginEntre != null) {

            model.addAttribute("id", true);
            model.addAttribute("nickName", loginEntre.getEntrepreNickName());

            model.addAttribute("userId", loginEntre.getId());
            model.addAttribute("nickName", loginEntre.getEntrepreNickName());
            model.addAttribute("birthDate", loginEntre.getEntrepreBirthDate());
            model.addAttribute("name", loginEntre.getEntrepreName());
            model.addAttribute("location", loginEntre.getEntrepreLocation());
            model.addAttribute("phone", loginEntre.getEntreprePhone());

            return "myPage/myPageMain";


        } else if (loginUser != null) {

            model.addAttribute("id", true);
            model.addAttribute("nickName", loginUser.getUserNickName());

            model.addAttribute("userId", loginUser.getId());
            model.addAttribute("nickName", loginUser.getUserNickName());
            model.addAttribute("birthDate", loginUser.getUserBirthDate());
            model.addAttribute("name", loginUser.getUserName());
            model.addAttribute("location", loginUser.getUserLocation());
            model.addAttribute("phone", loginUser.getUserPhone());

            return "myPage/myPageMain";

        } else {

            model.addAttribute("id", false);

        }

        return "redirect:/login";


    }

    @GetMapping("/noteList")
    public String myArticleListP(Model model, @SessionAttribute(name = "id", required = false) String id) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);


       
        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)

        if (loginEntre != null) {

            model.addAttribute("id", true);
            model.addAttribute("nickName", loginEntre.getEntrepreNickName());

            model.addAttribute("userId", loginEntre.getId());
            model.addAttribute("nickName", loginEntre.getEntrepreNickName());
            model.addAttribute("birthDate", loginEntre.getEntrepreBirthDate());
            model.addAttribute("name", loginEntre.getEntrepreName());
            model.addAttribute("location", loginEntre.getEntrepreLocation());
            model.addAttribute("phone", loginEntre.getEntreprePhone());

            return "myPage/noteList";


        } else if (loginUser != null) {

            model.addAttribute("id", true);
            model.addAttribute("nickName", loginUser.getUserNickName());

            model.addAttribute("userId", loginUser.getId());
            model.addAttribute("nickName", loginUser.getUserNickName());
            model.addAttribute("birthDate", loginUser.getUserBirthDate());
            model.addAttribute("name", loginUser.getUserName());
            model.addAttribute("location", loginUser.getUserLocation());
            model.addAttribute("phone", loginUser.getUserPhone());

            return "myPage/noteList";

        } else {

            model.addAttribute("id", false);

        }

        return "redirect:/login";


    }

}