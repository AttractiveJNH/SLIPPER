package com.example.Slipper.controller.review;


import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import com.example.Slipper.entity.reviewEntity.ReviewBoard;
import com.example.Slipper.repository.promotionRepository.PromotionBoardCommentRepository;
import com.example.Slipper.repository.promotionRepository.PromotionBoardRepository;
import com.example.Slipper.repository.reviewRepository.ReviewBoardCommentRepository;
import com.example.Slipper.repository.reviewRepository.ReviewBoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class ReviewDelete {

    @Autowired
    ReviewBoardRepository reviewBoardRepository; // 홍보글 데이터

    @Autowired
    ReviewBoardCommentRepository revComnt; // 댓글 데이터

    @GetMapping("/review/revDelete/{Id}")
    public String eventDelete(@PathVariable int Id){

        ReviewBoard delTarget = reviewBoardRepository.findByRevBrdPostId(Id);

        if(delTarget != null){
            reviewBoardRepository.delete(delTarget);
        }

        return "redirect:/review/main";
    }

}
