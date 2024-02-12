package com.example.Slipper.controller.promotion;


import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.promotionRepository.PromotionBoardRepository;
import com.example.Slipper.repository.userAndEntreRepositories.EntreRepository;
import com.example.Slipper.repository.userAndEntreRepositories.UserRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import com.example.Slipper.service.promotionService.PromotionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@Slf4j
public class PromotionMain { // 홍보 게시판 메인 화면의 컨트롤러

    @Autowired
    PromotionBoardRepository promotionBoardRepository;

    @Autowired
    PromotionService promotionService;

    @Autowired
    EntreService entreService;

    @Autowired
    UserService userService;

    @Autowired
    EntreRepository entreRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public PromotionMain(PromotionService promotionService){
        this.promotionService = promotionService;
    }




    // 홍보게시판 메인으로 들어오면 카드에 데이터 넣어주는 컨트롤러. 셀렉트박스 지역과 카테고리를 선택하면 조건에 맞는 내용만 보여준다.


    @GetMapping("/promotion")
    public String promotionMain(Model model,
                                @SessionAttribute(name = "id", required = false) String id,
                                @PageableDefault Pageable pageable) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);


        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {

            model.addAttribute("id", true);

            boolean isEntre = (loginEntre != null);
            model.addAttribute("isEntre", isEntre);

            //프로모션 정보 가져오기
            Page<PromotionBoard> boardList = promotionService.getPromotionBoardList(pageable);
            model.addAttribute("boardList", boardList);

            // 이미지 유무 체크
            List<PromotionBoard> imgboardList = promotionService.getPromotionBoardImgList(boardList);
            List<Integer> imageInfo = promotionService.getImageInfo(imgboardList);
            model.addAttribute("imageInfo", imageInfo);

            return "promotion/promotionMain";
        }


        return "redirect:/login";
    }

    // 지역 및 게시글 유형 선택 시 메핑
    @GetMapping("/promotion/sorting")
    public String promotionMainSortingPage(@RequestParam int region, @RequestParam int category,
                                         @PageableDefault Pageable pageable, Model model, @SessionAttribute(name = "id", required = false) String id) {

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        if (loginEntre != null || loginUser != null) {

            model.addAttribute("id", true);

            // 지역 값
            String regionName = promotionService.mapRegionToName(region);

            // 지역 및 게시글 유형 선택 시
            if (region == 0 && category == 0) {
                return "redirect:/promotion";

            } else if (region != 0 && category == 0) {
                // 설정한 지역에 맞는 게시글 정보
                Page<PromotionBoard> regionBoardList = promotionService.regionPromotionBoardList(regionName, pageable);

                // 이미지 유무 체크
                List<PromotionBoard> imgboardList = promotionService.getPromotionBoardImgList(regionBoardList);
                List<Integer> imageInfo = promotionService.getImageInfo(imgboardList);

                // 이미지 유무 체크 정보
                model.addAttribute("imageInfo", imageInfo);
                // 지역에 맞는 게시글 정보
                model.addAttribute("boardList", regionBoardList);
                // 카테고리 정보
                model.addAttribute("category", category);
                // 지역 정보
                model.addAttribute("region", region);

                return "promotion/promotionSortingMain";
            } else if (region == 0 && category != 0) {
                // 설정한 지역에 맞는 게시글 정보
                Page<PromotionBoard> categoryBoardList = promotionService.categoryPromotionBoardList(category, pageable);

                // 이미지 유무 체크
                List<PromotionBoard> imgboardList = promotionService.getPromotionBoardImgList(categoryBoardList);
                List<Integer> imageInfo = promotionService.getImageInfo(imgboardList);

                // 이미지 유무 체크 정보
                model.addAttribute("imageInfo", imageInfo);
                // 지역에 맞는 게시글 정보
                model.addAttribute("boardList", categoryBoardList);
                // 카테고리 정보
                model.addAttribute("category", category);
                // 지역 정보
                model.addAttribute("region", region);

                return "promotion/promotionSortingMain";
            } else {
                // 설정한 지역에 맞는 게시글 정보
                Page<PromotionBoard> sortingBoardList = promotionService.sortingPromotionBoardList(regionName, category, pageable);

                // 이미지 유무 체크
                List<PromotionBoard> imgboardList = promotionService.getPromotionBoardImgList(sortingBoardList);
                List<Integer> imageInfo = promotionService.getImageInfo(imgboardList);

                // 이미지 유무 체크 정보
                model.addAttribute("imageInfo", imageInfo);
                // 지역에 맞는 게시글 정보
                model.addAttribute("boardList", sortingBoardList);
                // 카테고리 정보
                model.addAttribute("category", category);
                // 지역 정보
                model.addAttribute("region", region);
                return "promotion/promotionSortingMain";
            }

        }
        return "redirect:/login";
    }

    // 검색 (카테고리 + 검색은 불가능)
    @GetMapping("/promotion/search")
    public String promotionSearch(@RequestParam("search_option") int searchOption,
                                @RequestParam("search") String search, @PageableDefault Pageable pageable, Model model,
                                @SessionAttribute(name = "id", required = false) String id){

        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        if (loginEntre != null || loginUser != null) {

            model.addAttribute("id", true);


            // searchOption == 1 : 제목만, 2: 제목 + 내용, 3: 글쓴이
            if (searchOption == 1) {
                // 검색에 맞는 게시글 정보
                Page<PromotionBoard> titleSearchBoardList = promotionService.titleSearchPromotionList(search, pageable);

                // 이미지 유무 체크
                List<PromotionBoard> imgboardList = promotionService.getPromotionBoardImgList(titleSearchBoardList);
                List<Integer> imageInfo = promotionService.getImageInfo(imgboardList);

                // 이미지 유무 체크 정보
                model.addAttribute("imageInfo", imageInfo);
                // 검색에 맞는 게시글 정보
                model.addAttribute("boardList", titleSearchBoardList);
                // 검색 조건 내용
                model.addAttribute("searchOption", searchOption);
                // 검색 내용
                model.addAttribute("search", search);

                return "promotion/promotionSearchMain";
            } else if (searchOption == 2) {
                // 검색에 맞는 게시글 정보
                Page<PromotionBoard> contentTitleSearchBoardList = promotionService.contentTitleSearchPromotionList(search, pageable);

                // 이미지 유무 체크
                List<PromotionBoard> imgboardList = promotionService.getPromotionBoardImgList(contentTitleSearchBoardList);
                List<Integer> imageInfo = promotionService.getImageInfo(imgboardList);

                // 이미지 유무 체크 정보
                model.addAttribute("imageInfo", imageInfo);
                // 검색에 맞는 게시글 정보
                model.addAttribute("boardList", contentTitleSearchBoardList);
                // 검색 조건 내용
                model.addAttribute("searchOption", searchOption);
                // 검색 내용
                model.addAttribute("search", search);

                return "promotion/promotionSearchMain";

            } else if (searchOption == 3) {
                // 검색에 맞는 게시글 정보
                Page<PromotionBoard> writerSearchBoardList = promotionService.writerSearchPromotionList(search, pageable);

                // 이미지 유무 체크
                List<PromotionBoard> imgboardList = promotionService.getPromotionBoardImgList(writerSearchBoardList);
                List<Integer> imageInfo = promotionService.getImageInfo(imgboardList);

                // 이미지 유무 체크 정보
                model.addAttribute("imageInfo", imageInfo);
                // 검색에 맞는 게시글 정보
                model.addAttribute("boardList", writerSearchBoardList);
                // 검색 조건 내용
                model.addAttribute("searchOption", searchOption);
                // 검색 내용
                model.addAttribute("search", search);

                return "promotion/promotionSearchMain";


            } else {
                //오류
                return "redirect:/promotion";
            }
        }
        return "redirect:/login";
    }

}
