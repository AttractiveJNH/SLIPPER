package com.example.Slipper.controller.promotion;


import com.example.Slipper.entity.promotionEntity.Promotion;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.promotionRepository.PromotionRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import com.example.Slipper.service.promotionService.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import java.util.ArrayList;


@Controller
@Slf4j
public class PromotionMain { // 홍보 게시판 메인 화면의 컨트롤러

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    PromotionService promotionService;

    @Autowired
    EntreService entreService;

    @Autowired
    UserService userService;



    // 홍보게시판 메인으로 들어오면 카드에 데이터 넣어주는 컨트롤러. 셀렉트박스 지역과 카테고리를 선택하면 조건에 맞는 내용만 보여준다.


    @GetMapping("/promotion")
    public String promotionMain(Model model,
                                @SessionAttribute(name = "id", required = false) String id) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {

            model.addAttribute("id", true);

            ArrayList<Promotion> promotions = promotionRepository.findAll();

            model.addAttribute("promotions", promotions);



            return "promotion/promotionMain";
        }


            return "redirect:/login";
        }

     //지역이나 카테고리를 선택하면 그에 맞게 비동기적으로 페이지를 수정해주는.
    @GetMapping("/updatePromotion")
    public String updatePromotion(@RequestParam(name = "promoBrdRegion", required = false) String promoBrdRegion,
                                  @RequestParam(name = "promoBrdCategory", required = false) Integer promoBrdCategory,
                                  Model model, @PageableDefault(page = 0, size = 8, sort = "promoBrdPostId", direction = Sort.Direction.DESC) Pageable pageable) {



//        model.addAttribute("promotions", promotionService.promotionList(pageable));

        log.info("promoBrdRegion: {}, promoBrdCategory: {}", promoBrdRegion, promoBrdCategory);
        // Promotion테이블에 있는 데이터를 지역과 카테고리별로 필터링해서 가져왔으면 좋겠다.
        Page<Promotion> promotions;

        if (promoBrdRegion != null && promoBrdCategory != null) {
            promotions = promotionRepository.findByPromoBrdRegionAndPromoBrdCategory(promoBrdRegion, promoBrdCategory, pageable);

        } else if (promoBrdRegion != null) {
            promotions = promotionRepository.findByPromoBrdRegion(promoBrdRegion, pageable);
        } else if (promoBrdCategory != null) {
            promotions = promotionRepository.findByPromoBrdCategory(promoBrdCategory, pageable);
        } else {
            promotions = promotionRepository.findAll(pageable);



            model.addAttribute("promotions", promotions);


            return "promotion/promotionData";
        }



//        int nowPage = promotions.getPageable().getPageNumber() + 1;
//        int startPage = Math.max(nowPage - 4, 1);
//        int endPage = Math.min(nowPage + 5, promotions.getTotalPages());

        model.addAttribute("promotions", promotions);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);

        return "promotion/promotionData"; // 업데이트할 부분의 뷰 페이지를 반환
    }
}
//    @GetMapping("/updateTest") 테스트용 컨트롤러
//    public String updateTest(Model model, @PageableDefault(page = 0, size = 8, sort = "promoBrdPostId", direction = Sort.Direction.DESC) Pageable pageable){
//
//        Page<Promotion> promotions = promotionRepository.findAll(pageable);
//
//        int nowPage = promotions.getPageable().getPageNumber() + 1;
//        int startPage = Math.max(nowPage - 4, 1);
//        int endPage = Math.min(nowPage + 5, promotions.getTotalPages());
//
//        model.addAttribute("promotions", promotions);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("endPage", endPage);
//
//        return "/promotion/promotionMain";
//    }

//    @GetMapping("/updatePromotion")
//    public String updatePromotion(@RequestParam(name = "promoBrdRegion", required = false) String promoBrdRegion,
//                                  @RequestParam(name = "promoBrdCategory", required = false) Integer promoBrdCategory,
//                                  Model model) {
//
//        log.info("promoBrdRegion: {}, promoBrdCategory: {}", promoBrdRegion, promoBrdCategory);
//        // Promotion테이블에 있는 데이터를 지역과 카테고리별로 필터링해서 가져왔으면 좋겠다.
//        ArrayList<Promotion> promotions;
//        if (promoBrdRegion != null && promoBrdCategory != null) {
//            promotions = new ArrayList<>(promotionRepository.findByPromoBrdRegionAndPromoBrdCategory(promoBrdRegion, promoBrdCategory));
//        } else if (promoBrdRegion != null) {
//            promotions = new ArrayList<>(promotionRepository.findByPromoBrdRegion(promoBrdRegion));
//        } else if (promoBrdCategory != null) {
//            promotions = new ArrayList<>(promotionRepository.findByPromoBrdCategory(promoBrdCategory));
//        } else {
//            promotions = new ArrayList<>(promotionRepository.findAll());
//            model.addAttribute("promotions", promotions);
//            log.info(promotions.toString());
//            return "redirect:/promotion/promotionMain";
//        }
//        log.info(promotions.toString());
//
//        model.addAttribute("promotions", promotions);
//
//        log.info(promotions.toString());
//
//        return "/promotion/promotionData"; // 업데이트할 부분의 뷰 페이지를 반환
//    }

    // 글쓰기 버튼 누르면 글쓰기 페이지로 넘어가는 컨트롤러.


    // 카드 누르면 홍보게시판 상세 정보 보여주는 페이지로 넘어가는 컨트롤러.


    // 제목, 내용, 제목 + 내용 선택하는 드롭다운 박스 컨트롤러.


    // 검색상자에 내용 입력하고 검색 누르면 해당 문자열 검색해주는 컨트롤러.


