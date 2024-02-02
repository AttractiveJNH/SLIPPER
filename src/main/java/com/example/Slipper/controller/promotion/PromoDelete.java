package com.example.Slipper.controller.promotion;


import com.example.Slipper.entity.promotionEntity.Promotion;
import com.example.Slipper.repository.promotionRepository.PromotionBoardCommentRepository;
import com.example.Slipper.repository.promotionRepository.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class PromoDelete {

    @Autowired
    PromotionRepository promotionRepository; // 홍보글 데이터

    @Autowired
    PromotionBoardCommentRepository proComnt; // 댓글 데이터

    @GetMapping("/promotion/eventDelete/{Id}")
    public String eventDelete(@PathVariable int Id){

        Promotion delTarget = promotionRepository.findByPromoBrdPostId(Id);

        if(delTarget != null){
            promotionRepository.delete(delTarget);
        }

        return "redirect:/promotion";
    }

    @GetMapping("/promotion/genDelete/{Id}")
    public String genDelete(@PathVariable int Id){

        Promotion delTarget = promotionRepository.findByPromoBrdPostId(Id);

        if(delTarget != null){
            promotionRepository.delete(delTarget);
        }

        return "redirect:/promotion";
    }

}
