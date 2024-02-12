package com.example.Slipper.controller.promotion;

import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import com.example.Slipper.entity.promotionEntity.PromotionBoardComment;
import com.example.Slipper.repository.promotionRepository.PromotionBoardCommentRepository;
import com.example.Slipper.repository.promotionRepository.PromotionBoardRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;

@Controller
@Slf4j
public class PromotionGenDetail {

    @Autowired
    PromotionBoardRepository promotionBoardRepository;

    @Autowired
    PromotionBoardCommentRepository promotionBoardCommentRepository;

    @Autowired
    EntreService entreService;

    @Autowired
    UserService userService;

    @GetMapping("/promotion/gendetail/{promoBrdPostId}")
    public String proGenDetail(@PathVariable(name = "promoBrdPostId") int promoBrdPostId, Model model,
                               @SessionAttribute(name = "id", required = false) String id) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        if (loginEntre != null || loginUser != null) {

            model.addAttribute("id", true);

            // 홍보 데이터 불러오기.
            PromotionBoard promotionBoard = promotionBoardRepository.findByPromoBrdPostId(promoBrdPostId);
            model.addAttribute("promotionBoard", promotionBoard);

            // 조회수
            promotionBoard.setPromoBrdViewCount(promotionBoard.getPromoBrdViewCount() + 1);
            promotionBoardRepository.save(promotionBoard);

            // 댓글 데이터 불러오기.
            ArrayList<PromotionBoardComment> proComnt = promotionBoardCommentRepository.findByPromoBrdPostId(promoBrdPostId);
            model.addAttribute("proComnt", proComnt);

            //세션 아이디와 댓글 작성자의 아이디 비교하여 삭제버튼 표시 여부 설정
            model.addAttribute("sessionId", id);

            return "promotion/proGenDetail";

        }

        return "/promotion/error";

    }
}
