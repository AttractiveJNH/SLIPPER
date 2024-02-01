package com.example.Slipper.controller.promotion;

import com.example.Slipper.dto.promotionDto.PromoComntDto;
import com.example.Slipper.entity.UserEntity;
import com.example.Slipper.entity.promotionEntity.Promotion;
import com.example.Slipper.entity.promotionEntity.PromotionBoardComment;
import com.example.Slipper.repository.promotionRepository.PromotionBoardCommentRepository;
import com.example.Slipper.repository.promotionRepository.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
@Slf4j
public class PromotionEventDetail {

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    PromotionBoardCommentRepository promoBrdComntRepository;

    @GetMapping("/promotion/eventdetail/{promoBrdPostId}")
    public String proEventDetail(@PathVariable(name = "promoBrdPostId") int promoBrdPostId, Model model){ // 나중에 requestvalue로 바꿔야 될지도..

        // 홍보 데이터 불러오기.
        Promotion promotions = promotionRepository.findByPromoBrdPostId(promoBrdPostId);
        model.addAttribute("promotions", promotions);

        // 조회수
        promotions.setPromoBrdViewCount(promotions.getPromoBrdViewCount() + 1);
        promotionRepository.save(promotions);

        // 댓글 데이터 불러오기.
        ArrayList<PromotionBoardComment> promoComnts = promoBrdComntRepository.findByPromoBrdPostId(promoBrdPostId);
        model.addAttribute("promoComnts", promoComnts);

        return "/promotion/proEventDetail";
    }


}
