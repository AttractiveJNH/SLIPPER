package com.example.Slipper.controller.review;

import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import com.example.Slipper.entity.promotionEntity.PromotionBoardComment;
import com.example.Slipper.entity.reviewEntity.ReviewBoard;
import com.example.Slipper.entity.reviewEntity.ReviewBoardComment;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.reviewRepository.ReviewBoardCommentRepository;
import com.example.Slipper.repository.reviewRepository.ReviewBoardRepository;
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
public class ReviewDetail {

    @Autowired
    ReviewBoardRepository reviewBoardRepository;

    @Autowired
    ReviewBoardCommentRepository reviewBoardCommentRepository;

    @Autowired
    EntreService entreService;

    @Autowired
    UserService userService;

    @GetMapping("/review/revDetail/{revBrdPostId}")
    public String proGenDetail(@PathVariable(name = "revBrdPostId") int revBrdPostId, Model model,
                               @SessionAttribute(name = "id", required = false) String id) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        if (loginEntre != null || loginUser != null) {

            model.addAttribute("id", true);

            // 홍보 데이터 불러오기.
            ReviewBoard reviewBoard = reviewBoardRepository.findByRevBrdPostId(revBrdPostId);
            model.addAttribute("reviewBoard", reviewBoard);

            // 조회수
            reviewBoard.setRevBrdViews(reviewBoard.getRevBrdViews() + 1);
            reviewBoardRepository.save(reviewBoard);

            // 댓글 데이터 불러오기.
            ArrayList<ReviewBoardComment> revComnt = reviewBoardCommentRepository.findByRevBrdPostId(revBrdPostId);
            model.addAttribute("revComnt", revComnt);

            //세션 아이디와 댓글 작성자의 아이디 비교하여 삭제버튼 표시 여부 설정
            model.addAttribute("sessionId", id);

            return "review/reviewDetail";

        }

        return "/review/error";

    }
}
