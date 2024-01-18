package com.example.Slipper.controller.promotion;

import com.example.Slipper.dto.promotionDto.PromoCreateDto;
import com.example.Slipper.entity.promotionEntity.Promotion;
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

    @GetMapping("/promotion/genCreate")
    public String proGen(){

        return "/promotion/proGenCreate";
    }

    @PostMapping("/promotion/genCreate")
    public String proGenCreate(PromoCreateDto promotionForm){
        log.info(promotionForm.toString());

        Promotion promotion = promotionForm.toEntity();
        log.info(promotion.toString());

        Promotion saved = promotionRepository.save(promotion);
        log.info(saved.toString());

        return "/promotion/proGenCreate";
    }



    @GetMapping("/promotion/eventCreate")
    public String proEvent(){

        return "/promotion/proEventCreate";
    }

    @PostMapping("/promotion/eventCreate")
    public String proEventCreate(PromoCreateDto promotionForm){
        log.info(promotionForm.toString());

        Promotion promotion = promotionForm.toEntity();
        log.info(promotion.toString());

        Promotion saved = promotionRepository.save(promotion);
        log.info(saved.toString());

        return "/promotion/proEventCreate";
    }
}
