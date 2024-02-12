package com.example.Slipper.controller.review;


import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import com.example.Slipper.entity.reviewEntity.ReviewBoard;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.promotionRepository.PromotionBoardRepository;
import com.example.Slipper.repository.reviewRepository.ReviewBoardRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class ReviewEdit {

    @Autowired
    ReviewBoardRepository reviewBoardRepository;

    @Autowired
    EntreService entreService;

    @Autowired
    UserService userService;

    @GetMapping("/review/edit/{Id}")
    public String revEdit(@PathVariable int Id, Model model, @SessionAttribute(name = "id", required = false) String id) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);


        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {

            model.addAttribute("id", true);
            int revBrdPostId = Id; // 변수명만 변경
            ReviewBoard reviewEdit = reviewBoardRepository.findByRevBrdPostId(revBrdPostId);
            model.addAttribute("revEdit", reviewEdit);

            return "review/reviewEdit";
        }
        return "redirect:/login";
    }

    // edit페이지의 필드의 값을 수정해서 target데이터에 교체해주는 컨트롤러.
    @PostMapping("/review/editSave/{Id}")
    public String revEditSave(@PathVariable int Id, @ModelAttribute("review") ReviewBoard revModi, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "입력값이 유효하지 않습니다.");
        }

        // DB의 데이터 받아와서 뷰 페이지에서 받아온 필드의 값으로 대체.
        ReviewBoard target = reviewBoardRepository.findByRevBrdPostId(Id);
        if (target != null) {
            target.setRevBrdTitle(revModi.getRevBrdTitle());
            target.setRevBrdRegion(revModi.getRevBrdRegion());
            target.setRevBrdCategory(revModi.getRevBrdCategory());
            target.setRevBrdArea(revModi.getRevBrdArea());
            target.setRevBrdContent(revModi.getRevBrdContent());

            reviewBoardRepository.save(target);
        }

        return "redirect:/review/revDetail/{Id}";
    }
}
