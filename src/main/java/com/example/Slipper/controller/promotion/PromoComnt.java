package com.example.Slipper.controller.promotion;

import com.example.Slipper.dto.promotionDto.PromoComntDto;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.entity.promotionEntity.PromotionBoardComment;
import com.example.Slipper.repository.promotionRepository.PromotionBoardCommentRepository;
import com.example.Slipper.repository.userAndEntreRepositories.EntreRepository;
import com.example.Slipper.repository.userAndEntreRepositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.Optional;

@Controller
@Slf4j
public class PromoComnt {

    @Autowired
    PromotionBoardCommentRepository promotionBoardCommentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntreRepository entreRepository;



    // 일반 게시판 댓글 달기
    @PostMapping("/promotion/genComntSave")
    public String genComntSave(@ModelAttribute PromoComntDto comnt,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes,
                               @SessionAttribute(name = "id", required = false) String id) {

        log.info("Session ID: {}", id); // 세션에서 받아온 아이디를 확인



        if (id == null || id.isEmpty()) {
            // id가 비어있거나 null일 경우 예외 처리 (원하는 동작 추가)
            model.addAttribute("errorMsg", "유효한 사용자 또는 사업자 ID가 제공되지 않았습니다.");
            return "/promotion/error";
        }

        // userId, entrepreId를 이용하여 사용자 정보 가져오기
        UserEntity user = userRepository.findById(id).orElse(null);
        EntreEntity entrepreneur = entreRepository.findById(id).orElse(null);

        if (user == null && entrepreneur == null) {
            // user와 entrepreneur 모두 null인 경우 예외 처리 (원하는 동작 추가)
            model.addAttribute("errorMsg", "일치하는 사용자 또는 사업자 ID가 없습니다.");
            return "/promotion/error";
        }

        // userNickName, entrepreNickName 추출
        String userNickName = (user != null) ? user.getUserNickName() : null;
        String entrepreNickName = (entrepreneur != null) ? entrepreneur.getEntrepreNickName() : null;
        String userId = (user != null) ? user.getId() : null;
        String entrepreId = (entrepreneur != null) ? entrepreneur.getId() : null;

        // DTO에 NickName 설정
        comnt.setUserNickName(userNickName);
        comnt.setEntrepreNickName(entrepreNickName);
        comnt.setUserId(userId);
        comnt.setEntrepreId(entrepreId);

        //세션 아이디와 댓글 작성자의 아이디 비교하여 삭제버튼 표시 여부 설정
        model.addAttribute("sessionId", id);

        if (result.hasErrors()) {
            model.addAttribute("errorMsg", "댓글 작성에 실패하였습니다.");
            return "/promotion/error";
        }

        // dto를 엔티티로
        PromotionBoardComment genComnt = comnt.toComnt();

        // 엔티티를 DB로
        promotionBoardCommentRepository.save(genComnt);
        log.info(genComnt.toString());

        // promoBrdPostId를 모델에 추가
        redirectAttributes.addAttribute("promoBrdPostId", comnt.getPromoBrdPostId());
        // 모델에 userNickName을 추가
        redirectAttributes.addAttribute("userNickName", comnt.getUserNickName());
        // 모델에 entrepreNickName을 추가 (entrepreneur 테이블의 닉네임이 필요할 경우)
        redirectAttributes.addAttribute("entrepreNickName", comnt.getEntrepreNickName());

        return "redirect:/promotion/gendetail/{promoBrdPostId}";
    }


    // 일반 게시판 댓글 삭제하기
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












    // 체험, 이벤트 게시글에 댓글 달기
    @PostMapping("/promotion/eventComntSave")
    public String eventComntSave(@ModelAttribute PromoComntDto comnt,
                                 BindingResult result,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 @SessionAttribute(name = "id", required = false) String id){

        log.info("Session ID: {}", id); // 세션에서 받아온 아이디를 확인



        if (id == null || id.isEmpty()) {
            // id가 비어있거나 null일 경우 예외 처리 (원하는 동작 추가)
            model.addAttribute("errorMsg", "유효한 사용자 또는 사업자 ID가 제공되지 않았습니다.");
            return "/promotion/error";
        }


        // userId, entrepreId를 이용하여 사용자 정보 가져오기
        UserEntity user = userRepository.findById(id).orElse(null);
        EntreEntity entrepreneur = entreRepository.findById(id).orElse(null);

        if (user == null && entrepreneur == null) {
            // user와 entrepreneur 모두 null인 경우 예외 처리 (원하는 동작 추가)
            model.addAttribute("errorMsg", "일치하는 사용자 또는 사업자 ID가 없습니다.");
            return "/promotion/error";
        }

        // userNickName, entrepreNickName 추출
        String userNickName = (user != null) ? user.getUserNickName() : null;
        String entrepreNickName = (entrepreneur != null) ? entrepreneur.getEntrepreNickName() : null;
        String userId = (user != null) ? user.getId() : null;
        String entrepreId = (entrepreneur != null) ? entrepreneur.getId() : null;


        // DTO에 NickName 설정, Id설정
        comnt.setUserNickName(userNickName);
        comnt.setEntrepreNickName(entrepreNickName);
        comnt.setUserId(userId);
        comnt.setEntrepreId(entrepreId);

        //세션 아이디와 댓글 작성자의 아이디 비교하여 삭제버튼 표시 여부 설정
        model.addAttribute("sessionId", id);

        if (result.hasErrors()) {
            model.addAttribute("errorMsg", "댓글 작성에 실패하였습니다.");
            return "/promotion/error";
        }


        // dto를 엔티티로
        PromotionBoardComment eventComnt = comnt.toComnt();
        log.info(eventComnt.toString());

        // 엔티티를 DB로
        promotionBoardCommentRepository.save(eventComnt);
        log.info(eventComnt.toString());

        // promoBrdPostId를 모델에 추가
        redirectAttributes.addAttribute("promoBrdPostId", comnt.getPromoBrdPostId());

        return "redirect:/promotion/eventdetail/{promoBrdPostId}";
    }


    // 체험, 이벤트 게시글에 댓글 삭제하기
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
