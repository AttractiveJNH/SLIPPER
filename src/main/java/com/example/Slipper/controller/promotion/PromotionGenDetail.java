package com.example.Slipper.controller.promotion;

import com.example.Slipper.entity.UserEntity;
import com.example.Slipper.entity.promotionEntity.Promotion;
import com.example.Slipper.entity.promotionEntity.PromotionBoardComment;
import com.example.Slipper.repository.UserRepository;
import com.example.Slipper.repository.promotionRepository.PromotionBoardCommentRepository;
import com.example.Slipper.repository.promotionRepository.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;

@Controller
@Slf4j
public class PromotionGenDetail {

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    PromotionBoardCommentRepository promotionBoardCommentRepository;

    @GetMapping("/promotion/gendetail/{promoBrdPostId}")
    public String proGenDetail(@PathVariable (name = "promoBrdPostId") int promoBrdPostId, Model model){


        // 홍보 데이터 불러오기.
        Promotion promotion = promotionRepository.findByPromoBrdPostId(promoBrdPostId);
        model.addAttribute("promotion", promotion);

        // 조회수
        promotion.setPromoBrdViewCount(promotion.getPromoBrdViewCount() + 1);
        promotionRepository.save(promotion);

        // 댓글 데이터 불러오기.
        ArrayList<PromotionBoardComment> proComnt = promotionBoardCommentRepository.findByPromoBrdPostId(promoBrdPostId);
        model.addAttribute("proComnt", proComnt);

        return "/promotion/proGenDetail";
    }

}
