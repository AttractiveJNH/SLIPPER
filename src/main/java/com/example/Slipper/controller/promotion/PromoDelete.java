package com.example.Slipper.controller.promotion;


import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import com.example.Slipper.repository.promotionRepository.PromotionBoardCommentRepository;
import com.example.Slipper.repository.promotionRepository.PromotionBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class PromoDelete {

    @Autowired
    PromotionBoardRepository promotionBoardRepository; // 홍보글 데이터

    @Autowired
    PromotionBoardCommentRepository proComnt; // 댓글 데이터

    @GetMapping("/promotion/eventDelete/{Id}")
    public String eventDelete(@PathVariable int Id){

        PromotionBoard delTarget = promotionBoardRepository.findByPromoBrdPostId(Id);

        if(delTarget != null){
            promotionBoardRepository.delete(delTarget);
        }

        return "redirect:/promotion";
    }

    @GetMapping("/promotion/genDelete/{Id}")
    public String genDelete(@PathVariable int Id){

        PromotionBoard delTarget = promotionBoardRepository.findByPromoBrdPostId(Id);

        if(delTarget != null){
            promotionBoardRepository.delete(delTarget);
        }

        return "redirect:/promotion";
    }

}
