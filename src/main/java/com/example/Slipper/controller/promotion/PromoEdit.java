package com.example.Slipper.controller.promotion;

import com.example.Slipper.entity.promotionEntity.Promotion;
import com.example.Slipper.repository.promotionRepository.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class PromoEdit {

    @Autowired
    PromotionRepository promotionRepository;

    // event로 등록된 홍보게시물의 Id값을 기준으로 데이터 불러와서 eventEdit페이지에 등록.
    @GetMapping("/promotion/eventEdit/{Id}")
    public String eventEdit(@PathVariable int Id, Model model){
        int promoBrdPostId = Id; // 변수명 변경.
        Promotion proEventEdit = promotionRepository.findByPromoBrdPostId(promoBrdPostId);
        model.addAttribute("eventEdit", proEventEdit);

        return "promotion/eventEdit";
    }

    // edit페이지의 필드의 값을 수정해서 target데이터에 교체해주는 컨트롤러.
    @PostMapping("/promotion/eventEditSave/{Id}")
    public String eventEditSave(@PathVariable int Id, @ModelAttribute("promotion") Promotion proModi, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("error", "입력값이 유효하지 않습니다.");
        }

        // DB의 데이터 받아와서 뷰 페이지에서 받아온 필드의 값으로 대체.
        Promotion target = promotionRepository.findByPromoBrdPostId(Id);
        if(target != null){
            target.setPromoBrdTitle(proModi.getPromoBrdTitle());
            target.setPromoBrdRegion(proModi.getPromoBrdRegion());
            target.setPromoBrdCategory(proModi.getPromoBrdCategory());
            target.setPromoBrdBusinessName(proModi.getPromoBrdBusinessName());
            target.setPromoBrdExperienceDate(proModi.getPromoBrdExperienceDate());
            target.setPromoBrdExpSelect(proModi.getPromoBrdExpSelect());
            target.setPromoBrdApplyStartDate(proModi.getPromoBrdApplyStartDate());
            target.setPromoBrdApplyEndDate(proModi.getPromoBrdApplyEndDate());
            target.setPromoBrdEventStartDate(proModi.getPromoBrdEventStartDate());
            target.setPromoBrdEventEndDate(proModi.getPromoBrdEventEndDate());
            target.setPromoBrdReviewStart(proModi.getPromoBrdReviewStart());
            target.setPromoBrdReviewDeadline(proModi.getPromoBrdReviewDeadline());
            target.setPromoBrdExperienceProvided(proModi.getPromoBrdExperienceProvided());
            target.setPromoBrdContent(proModi.getPromoBrdContent());
            target.setPromoBrdMainImg(proModi.getPromoBrdMainImg());
            target.setPromoBrdMaxParticipants(proModi.getPromoBrdMaxParticipants());

            promotionRepository.save(target);
        }

        return "redirect:/promotion/eventdetail/{Id}";
    }

    // gen으로 등록된 홍보게시물의 Id값을 기준으로 데이터 불러와서 genEdit페이지에 등록.
    @GetMapping("/promotion/genEdit/{Id}")
    public String genEdit(@PathVariable int Id, Model model){
        int promoBrdPostId = Id; // 변수명만 변경
        Promotion proGenEdit = promotionRepository.findByPromoBrdPostId(promoBrdPostId);
        model.addAttribute("genEdit", proGenEdit);

        return "promotion/genEdit";
    }

    // edit페이지의 필드의 값을 수정해서 target데이터에 교체해주는 컨트롤러.
    @PostMapping("/promotion/genEditSave/{Id}")
    public String genEditSave(@PathVariable int Id, @ModelAttribute("promotion") Promotion proModi, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){
            model.addAttribute("error", "입력값이 유효하지 않습니다.");
        }

        // DB의 데이터 받아와서 뷰 페이지에서 받아온 필드의 값으로 대체.
        Promotion target = promotionRepository.findByPromoBrdPostId(Id);
        if(target != null){
            target.setPromoBrdTitle(proModi.getPromoBrdTitle());
            target.setPromoBrdRegion(proModi.getPromoBrdRegion());
            target.setPromoBrdCategory(proModi.getPromoBrdCategory());
            target.setPromoBrdBusinessName(proModi.getPromoBrdBusinessName());
            target.setPromoBrdArea(proModi.getPromoBrdArea());
            target.setPromoBrdContent(proModi.getPromoBrdContent());
            target.setPromoBrdMainImg(proModi.getPromoBrdMainImg());

            promotionRepository.save(target);
        }

        return "redirect:/promotion/gendetail/{Id}";
    }

}
