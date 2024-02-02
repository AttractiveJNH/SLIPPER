package com.example.Slipper.controller.promotion;

import com.example.Slipper.dto.promotionDto.PromoCreateDto;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.promotionEntity.Promotion;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.promotionRepository.PromotionRepository;
import com.example.Slipper.repository.userAndEntreRepositories.EntreRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Optional;

@Controller
@Slf4j
public class PromotionCreate {

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    EntreRepository entreRepository;

    @Autowired
    EntreService entreService;

    @Autowired
    UserService userService;

    @GetMapping("/promotion/genCreate")
    public String proGen(@SessionAttribute(name = "id", required = false) String id, Model model) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);

            return "/promotion/proGenCreate";
        }
        return "redirect:/login";
    }
    @PostMapping("/promotion/genCreate")
    public String proGenCreate(PromoCreateDto promotionForm, @SessionAttribute(name = "id", required = false) String id, Model model){


        log.info("Controller On");
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);

        log.info("ID : " + id);

        UserEntity loginUser = userService.getLoginUserById(id);

        log.info("ID : " + id);

        // id에 해당하는 값이 null인 경우 로그인 화면으로 전환.
        if (loginEntre != null || loginUser != null) {

            log.info("If On");
            model.addAttribute("id", true);
            Promotion promotion = promotionForm.toEntity(id);

            log.info("If On");

            log.info(promotion.toString());
            promotionRepository.save(promotion);

            return "redirect:/promotion";
        }
        return "redirect:/login";
    }



    @GetMapping("/promotion/eventCreate")
    public String proEvent(){

        return "/promotion/proEventCreate";
    }

    @PostMapping("/promotion/eventCreate")
    public String proEventCreate(PromoCreateDto promotionForm, @SessionAttribute(name = "id", required = false) String id){

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);
        EntreEntity entre = entreRepository.findById(id).orElse(null);

        Promotion promotion = promotionForm.toEntity(id);
        log.info(promotion.toString());

        promotionRepository.save(promotion);

        return "redirect:/promotion";
    }

}
