package com.example.Slipper.controller.promotion;

import com.example.Slipper.dto.promotionDto.PromoCreateDto;
import com.example.Slipper.entity.EntreEntity;
import com.example.Slipper.entity.SswTestEntity.Entrepreneur;
import com.example.Slipper.entity.promotionEntity.Promotion;
import com.example.Slipper.repository.EntreRepository;
import com.example.Slipper.repository.EntrepreneurRepository;
import com.example.Slipper.repository.promotionRepository.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class PromotionCreate {

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    EntreRepository entreRepository;

    @GetMapping("/promotion/genCreate")
    public String proGen(){

        return "/promotion/proGenCreate";
    }

    @PostMapping("/promotion/genCreate")
    public String proGenCreate(PromoCreateDto promotionForm){
        EntreEntity entre = entreRepository.findByEntrepreNum(1);
        log.info(promotionForm.toString());

        Promotion promotion = promotionForm.toEntity(entre);
        log.info(promotion.toString());

        promotionRepository.save(promotion);

        return "redirect:/promotion";

    }



    @GetMapping("/promotion/eventCreate")
    public String proEvent(){

        return "/promotion/proEventCreate";
    }

    @PostMapping("/promotion/eventCreate")
    public String proEventCreate(PromoCreateDto promotionForm){
        log.info(promotionForm.toString());
        EntreEntity entre = entreRepository.findByEntrepreNum(1);

        Promotion promotion = promotionForm.toEntity(entre);
        log.info(promotion.toString());

        promotionRepository.save(promotion);

        return "redirect:/promotion";
    }

}
