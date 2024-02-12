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
public class PromotionEventDetail {

    @Autowired
    PromotionBoardRepository promotionBoardRepository;




    @Autowired
    PromotionBoardCommentRepository promotionBoardCommentRepository;

    @Autowired
    EntreService entreService;

    @Autowired
    UserService userService;

    @GetMapping("/promotion/eventdetail/{promoBrdPostId}")
    public String proEventDetail(@PathVariable(name = "promoBrdPostId") int promoBrdPostId, Model model,
                                 @SessionAttribute(value = "id", required = false) String id) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        if (loginEntre != null || loginUser != null) {

            model.addAttribute("id", true);

            // 홍보 데이터 불러오기.
            PromotionBoard promotionInfo = promotionBoardRepository.findByPromoBrdPostId(promoBrdPostId);

            // 해당 게시글 작성자 정보와 현재 로그인 중인 유저가 같은지 검사 (1: 현재 로그인 중인 유저 = 작성자, 2: 현재 로그인 중인 유저 = 참가 예정자)
//            EntreEntity entrepreneurWriter = promotionInfo.getClass().getAnnotation(t);
//            if(entrepreneurWriter != null){
//                if(entrepreneurWriter == loginEntre){
//                    model.addAttribute("Writer", 1);
//                } else {
//                    model.addAttribute("Writer", 2);
//                }
//
//            } else{
//                if(userWriter == loginUser){
//                    model.addAttribute("Writer", 1);
//                } else {
//                    model.addAttribute("Writer", 2);
//                }
//            }

            model.addAttribute("promotionInfo", promotionInfo);

            // 조회수
            promotionInfo.setPromoBrdViewCount(promotionInfo.getPromoBrdViewCount() + 1);
            promotionBoardRepository.save(promotionInfo);

            // 댓글 데이터 불러오기.
            ArrayList<PromotionBoardComment> promoComnts = promotionBoardCommentRepository.findByPromoBrdPostId(promoBrdPostId);
            model.addAttribute("promoComnts", promoComnts);

            //세션 아이디와 댓글 작성자의 아이디 비교하여 삭제버튼 표시 여부 설정
            model.addAttribute("sessionId", id);

            return "/promotion/proEventDetail";
        }
        return "/promotion/error";
    }

}
