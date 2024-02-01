package com.example.Slipper.controller.promotion;


import com.example.Slipper.entity.promotionEntity.Promotion;
import com.example.Slipper.repository.promotionRepository.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;


@Controller
@Slf4j
public class PromotionMain { // 홍보 게시판 메인 화면의 컨트롤러

    @Autowired
    PromotionRepository promotionRepository;

    // 홍보게시판 메인으로 들어오면 카드에 데이터 넣어주는 컨트롤러. 셀렉트박스 지역과 카테고리를 선택하면 조건에 맞는 내용만 보여준다.


    @GetMapping("/promotion")
    public String promotionMain(Model model){

        // Promotion테이블에 있는 데이터를 지역과 카테고리별로 필터링해서 가져왔으면 좋겠다.
        ArrayList<Promotion> promotions = promotionRepository.findAll();


        model.addAttribute("promotions", promotions);
        log.info(promotions.toString());

        return "/promotion/promotionMain";
    }

     //지역이나 카테고리를 선택하면 그에 맞게 비동기적으로 페이지를 수정해주는.
    @GetMapping("/updatePromotion")
    public String updatePromotion(@RequestParam(name = "promoBrdRegion", required = false) String promoBrdRegion,
                                  @RequestParam(name = "promoBrdCategory", required = false) Integer promoBrdCategory,
                                  Model model) {

        log.info("promoBrdRegion: {}, promoBrdCategory: {}", promoBrdRegion, promoBrdCategory);
        // Promotion테이블에 있는 데이터를 지역과 카테고리별로 필터링해서 가져왔으면 좋겠다.
        ArrayList<Promotion> promotions;
        if (promoBrdRegion != null && promoBrdCategory != null) {
            promotions = new ArrayList<>(promotionRepository.findByPromoBrdRegionAndPromoBrdCategory(promoBrdRegion, promoBrdCategory));
        } else if (promoBrdRegion != null) {
            promotions = new ArrayList<>(promotionRepository.findByPromoBrdRegion(promoBrdRegion));
        } else if (promoBrdCategory != null) {
            promotions = new ArrayList<>(promotionRepository.findByPromoBrdCategory(promoBrdCategory));
        } else {
            promotions = new ArrayList<>(promotionRepository.findAll());
            model.addAttribute("promotions", promotions);
            log.info(promotions.toString());
            return "redirect:/promotion/promotionMain";
        }
        log.info(promotions.toString());

        model.addAttribute("promotions", promotions);

        log.info(promotions.toString());

        return "promotion/promotionData"; // 업데이트할 부분의 뷰 페이지를 반환
    }

    // 글쓰기 버튼 누르면 글쓰기 페이지로 넘어가는 컨트롤러.


    // 카드 누르면 홍보게시판 상세 정보 보여주는 페이지로 넘어가는 컨트롤러.


    // 제목, 내용, 제목 + 내용 선택하는 드롭다운 박스 컨트롤러.


    // 검색상자에 내용 입력하고 검색 누르면 해당 문자열 검색해주는 컨트롤러.

}
