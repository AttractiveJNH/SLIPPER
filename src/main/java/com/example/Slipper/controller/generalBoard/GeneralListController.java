package com.example.Slipper.controller.generalBoard;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.generalEntity.GeneralBoard;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.generalRepository.GeneralBoardRepository;
import com.example.Slipper.service.generalBoardServices.GeneralService;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import com.example.Slipper.service.meetingBoardServices.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class GeneralListController {
    @Autowired
    private GeneralBoardRepository generalBoardRepository;

    private final UserService userService;

    private final EntreService entreService;

    private final GeneralService generalService;
    @Autowired
    public GeneralListController(GeneralService generalService, UserService userService, EntreService entreService){
        this.generalService = generalService;
        this.userService = userService;
        this.entreService = entreService;
    }

    // 일반 게시판 목록 페이지
    @GetMapping("/general/main")
    public String generalMainPage(@PageableDefault Pageable pageable, Model model,
                                  @SessionAttribute(name = "id", required = false) String id){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        }

        // 페이징 및 게시글 정보
        Page<GeneralBoard> boardList = generalService.getGeneralBoardList(pageable);
        model.addAttribute("boardList", boardList);

        // 이미지 유무 체크
        List<GeneralBoard> imgboardList = generalService.getGeneralBoardImgList(boardList);
        List<Integer> imageInfo = generalService.getImageInfo(imgboardList);
        model.addAttribute("imageInfo", imageInfo);

        return "general/generalMain";
    }

    // 일반 게시판 카테고리 설정 페이지
    @GetMapping("/general/sorting")
    public String generalMainSortingPage(@RequestParam int category, @PageableDefault Pageable pageable, Model model,
                                         @SessionAttribute(name = "id", required = false) String id){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        }

        // 지역 및 게시글 유형 선택 시
        if(category == 0){
            return "redirect:/general/main";
        } else {
            // 일반 카테고리
            Page<GeneralBoard> categoryBoardList = generalService.categoryGeneralBoardList(category, pageable);

            // 이미지 유무 체크
            List<GeneralBoard> imgboardList = generalService.getGeneralBoardImgList(categoryBoardList);
            List<Integer> imageInfo = generalService.getImageInfo(imgboardList);

            // 이미지 유무 체크 정보
            model.addAttribute("imageInfo", imageInfo);
            // 카테고리에 맞는 게시글 정보
            model.addAttribute("boardList", categoryBoardList);
            // 카테고리 정보
            model.addAttribute("category", category);

            return "general/generalSortingMain";
        }
    }

    // 검색 (카테고리 + 검색은 불가능)
    @GetMapping("/general/main/search")
    public String generalSearch(@RequestParam("search_option") int searchOption,
                                @RequestParam("search") String search, @PageableDefault Pageable pageable, Model model,
                                @SessionAttribute(name = "id", required = false) String id){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        }


        // searchOption == 1 : 제목만, 2: 제목 + 내용, 3: 글쓴이
        if(searchOption == 1){
            // 검색에 맞는 게시글 정보
            Page<GeneralBoard> titleSearchBoardList = generalService.titleSearchGeneralList(search, pageable);

            // 이미지 유무 체크
            List<GeneralBoard> imgboardList = generalService.getGeneralBoardImgList(titleSearchBoardList);
            List<Integer> imageInfo = generalService.getImageInfo(imgboardList);

            // 이미지 유무 체크 정보
            model.addAttribute("imageInfo", imageInfo);
            // 검색에 맞는 게시글 정보
            model.addAttribute("boardList", titleSearchBoardList);
            // 검색 조건 내용
            model.addAttribute("searchOption", searchOption);
            // 검색 내용
            model.addAttribute("search", search);

            return "general/generalSearchMain";
        } else if (searchOption == 2){
            // 검색에 맞는 게시글 정보
            Page<GeneralBoard> contentTitleSearchBoardList = generalService.contentTitleSearchGeneralList(search, pageable);

            // 이미지 유무 체크
            List<GeneralBoard> imgboardList = generalService.getGeneralBoardImgList(contentTitleSearchBoardList);
            List<Integer> imageInfo = generalService.getImageInfo(imgboardList);

            // 이미지 유무 체크 정보
            model.addAttribute("imageInfo", imageInfo);
            // 검색에 맞는 게시글 정보
            model.addAttribute("boardList", contentTitleSearchBoardList);
            // 검색 조건 내용
            model.addAttribute("searchOption", searchOption);
            // 검색 내용
            model.addAttribute("search", search);

            return "general/generalSearchMain";

        } else if (searchOption == 3){
            // 검색에 맞는 게시글 정보
            Page<GeneralBoard> writerSearchBoardList = generalService.writerSearchGeneralList(search, pageable);

            // 이미지 유무 체크
            List<GeneralBoard> imgboardList = generalService.getGeneralBoardImgList(writerSearchBoardList);
            List<Integer> imageInfo = generalService.getImageInfo(imgboardList);

            // 이미지 유무 체크 정보
            model.addAttribute("imageInfo", imageInfo);
            // 검색에 맞는 게시글 정보
            model.addAttribute("boardList", writerSearchBoardList);
            // 검색 조건 내용
            model.addAttribute("searchOption", searchOption);
            // 검색 내용
            model.addAttribute("search", search);

            return "general/generalSearchMain";


        } else {
            //오류
            return "redirect:/general/main";
        }
    }

}
