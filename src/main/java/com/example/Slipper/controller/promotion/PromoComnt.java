package com.example.Slipper.controller.promotion;

import com.example.Slipper.dto.promotionDto.PromoComntDto;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.entity.promotionEntity.PromotionBoardComment;
import com.example.Slipper.repository.promotionRepository.PromotionBoardCommentRepository;
import com.example.Slipper.repository.userAndEntreRepositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class PromoComnt {

    @Autowired
    PromotionBoardCommentRepository promotionBoardCommentRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/promotion/genComntSave")
    public String genComntSave(@ModelAttribute PromoComntDto comnt,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            @RequestParam String userId) {


        String id = userId;
        // userNum을 이용하여 사용자 정보 가져오기
        UserEntity user = userRepository.findById(id).orElse(null);

        // userNickName 추출
        String userNickName = user.getUserNickName();

        // DTO에 userNickName 설정
        comnt.setUserNickName(userNickName);
        log.info(userNickName.toString());

        if (result.hasErrors()) {
            model.addAttribute("errorMsg", "댓글 작성에 실패하였습니다.");
            return "/promotion/error";
        }

        // dto를 엔티티로
        PromotionBoardComment genComnt = comnt.toComnt();
        log.info(genComnt.toString());

        // 엔티티를 DB로
        promotionBoardCommentRepository.save(genComnt);
        log.info(genComnt.toString());

        // promoBrdPostId를 모델에 추가
        redirectAttributes.addAttribute("promoBrdPostId", comnt.getPromoBrdPostId());

        return "redirect:/promotion/gendetail/{promoBrdPostId}";
    }

    @GetMapping("/promotion/genComntDelete/{Id}")
    public String genComntDelete(@PathVariable int Id, @RequestParam int promoBrdPostId){

        PromotionBoardComment delTarget = promotionBoardCommentRepository.findByPromoBrdComntId(Id); // 이 부분은 userNum를 파라미터로 받는 부분으로 교체.

        if(delTarget != null){
            promotionBoardCommentRepository.delete(delTarget);
        }
        else {
            return "/promotion/error";
        }


        return "redirect:/promotion/gendetail/" + promoBrdPostId;
    }

    @PostMapping("/promotion/eventComntSave")
    public String eventComntSave(@ModelAttribute PromoComntDto comnt,
                            BindingResult result,
                            Model model,
                            RedirectAttributes redirectAttributes,
                            @RequestParam Long userNum){

        // userNum을 이용하여 사용자 정보 가져오기
        UserEntity user = userRepository.findByUserNum(userNum);

        // userNickName 추출
        String userNickName = user.getUserNickName();

        // DTO에 userNickName 설정
        comnt.setUserNickName(userNickName);
        log.info(userNickName.toString());

        if (result.hasErrors()) {
            model.addAttribute("errorMsg", "댓글 작성에 실패하였습니다.");
            return "/promotion/error";
        }


        // dto를 엔티티로
        PromotionBoardComment genComnt = comnt.toComnt();
        log.info(genComnt.toString());

        // 엔티티를 DB로
        promotionBoardCommentRepository.save(genComnt);
        log.info(genComnt.toString());

        // promoBrdPostId를 모델에 추가
        redirectAttributes.addAttribute("promoBrdPostId", comnt.getPromoBrdPostId());

        return "redirect:/promotion/eventdetail/{promoBrdPostId}";
    }

    @GetMapping("/promotion/eventComntDelete/{Id}")
    public String eventComntDelete(@PathVariable int Id, @RequestParam int promoBrdPostId){

        PromotionBoardComment delTarget = promotionBoardCommentRepository.findByPromoBrdComntId(Id); // 이 부분은 userNum를 파라미터로 받는 부분으로 교체.

        if(delTarget != null){
            promotionBoardCommentRepository.delete(delTarget);
        }
        else {
            return "/promotion/error";
        }


        return "redirect:/promotion/eventdetail/" + promoBrdPostId;
    }

}
