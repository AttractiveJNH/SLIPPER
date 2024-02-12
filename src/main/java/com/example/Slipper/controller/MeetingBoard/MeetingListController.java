package com.example.Slipper.controller.MeetingBoard;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.userAndEntreEntities.EntreEntity;
import com.example.Slipper.entity.userAndEntreEntities.UserEntity;
import com.example.Slipper.repository.MeetingBoardRepository;
import com.example.Slipper.service.loginAndJoinServices.EntreService;
import com.example.Slipper.service.loginAndJoinServices.UserService;
import com.example.Slipper.service.meetingBoardServices.MeetingService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MeetingListController {
    @Autowired
    MeetingBoardRepository meetingBoardRepository;

    private final MeetingService meetingService;

    private final UserService userService;

    private final EntreService entreService;


    @Autowired
    public MeetingListController(MeetingService meetingService, UserService userService, EntreService entreService){
        this.meetingService = meetingService;
        this.userService = userService;
        this.entreService = entreService;
    }

    //모임게시판 목록 페이지
    @GetMapping("/meeting/main")
    public String meetingMainPage(@PageableDefault Pageable pageable, Model model,
                                  @SessionAttribute(name = "id", required = false) String id){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        }

        // 페이징 및 게시글 정보
        Page<MeetingBoard> boardList = meetingService.getMeetingBoardList(pageable);
        model.addAttribute("boardList", boardList);

        // 이미지 유무 체크
        List<MeetingBoard> imgboardList = meetingService.getMeetingBoardImgList(boardList);
        List<Integer> imageInfo = meetingService.getImageInfo(imgboardList);
        model.addAttribute("imageInfo", imageInfo);

        return "meeting/meetingMain";
    }


    // 지역 및 게시글 유형 선택 시 메핑
    @GetMapping("/meeting/sorting")
    public String meetingMainSortingPage(@RequestParam int region, @RequestParam int category,
                                         @PageableDefault Pageable pageable, Model model,
                                         @SessionAttribute(name = "id", required = false) String id){
        // 로그인 정보
        EntreEntity loginEntre = entreService.getLoginEntreByLoginId(id);
        UserEntity loginUser = userService.getLoginUserById(id);

        // 세션값 유무에 따라 헤더변동(true = LogOut / false = 헤더 없음)
        if (loginEntre != null || loginUser != null) {
            model.addAttribute("id", true);
        }

        // 지역 값
        String regionName = meetingService.mapRegionToName(region);

        // 지역 및 게시글 유형 선택 시
        if(region == 0 && category == 0){
            return "redirect:/meeting/main";

        } else if(region != 0 && category == 0){
            // 설정한 지역에 맞는 게시글 정보
            Page<MeetingBoard> regionBoardList = meetingService.regionMeetingBoardList(regionName, pageable);

            // 이미지 유무 체크
            List<MeetingBoard> imgboardList = meetingService.getMeetingBoardImgList(regionBoardList);
            List<Integer> imageInfo = meetingService.getImageInfo(imgboardList);

            // 이미지 유무 체크 정보
            model.addAttribute("imageInfo", imageInfo);
            // 지역에 맞는 게시글 정보
            model.addAttribute("boardList", regionBoardList);
            // 카테고리 정보
            model.addAttribute("category", category);
            // 지역 정보
            model.addAttribute("region", region);

            return "meeting/meetingSortingMain";
        } else if(region == 0 && category != 0){
            // 설정한 지역에 맞는 게시글 정보
            Page<MeetingBoard> categoryBoardList = meetingService.categoryMeetingBoardList(category, pageable);

            // 이미지 유무 체크
            List<MeetingBoard> imgboardList = meetingService.getMeetingBoardImgList(categoryBoardList);
            List<Integer> imageInfo = meetingService.getImageInfo(imgboardList);

            // 이미지 유무 체크 정보
            model.addAttribute("imageInfo", imageInfo);
            // 지역에 맞는 게시글 정보
            model.addAttribute("boardList", categoryBoardList);
            // 카테고리 정보
            model.addAttribute("category", category);
            // 지역 정보
            model.addAttribute("region", region);

            return "meeting/meetingSortingMain";
        } else {
            // 설정한 지역에 맞는 게시글 정보
            Page<MeetingBoard> sortingBoardList = meetingService.sortingMeetingBoardList(regionName, category, pageable);

            // 이미지 유무 체크
            List<MeetingBoard> imgboardList = meetingService.getMeetingBoardImgList(sortingBoardList);
            List<Integer> imageInfo = meetingService.getImageInfo(imgboardList);

            // 이미지 유무 체크 정보
            model.addAttribute("imageInfo", imageInfo);
            // 지역에 맞는 게시글 정보
            model.addAttribute("boardList", sortingBoardList);
            // 카테고리 정보
            model.addAttribute("category", category);
            // 지역 정보
            model.addAttribute("region", region);
            return "meeting/meetingSortingMain";
        }
    }


    // 검색 (카테고리 + 검색은 불가능)
    @GetMapping("/meeting/main/search")
    public String meetingSearch(@RequestParam("search_option") int searchOption,
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
            Page<MeetingBoard> titleSearchBoardList = meetingService.titleSearchMeetingList(search, pageable);

            // 이미지 유무 체크
            List<MeetingBoard> imgboardList = meetingService.getMeetingBoardImgList(titleSearchBoardList);
            List<Integer> imageInfo = meetingService.getImageInfo(imgboardList);

            // 이미지 유무 체크 정보
            model.addAttribute("imageInfo", imageInfo);
            // 검색에 맞는 게시글 정보
            model.addAttribute("boardList", titleSearchBoardList);
            // 검색 조건 내용
            model.addAttribute("searchOption", searchOption);
            // 검색 내용
            model.addAttribute("search", search);

            return "meeting/meetingSearchMain";
        } else if (searchOption == 2){
            // 검색에 맞는 게시글 정보
            Page<MeetingBoard> contentTitleSearchBoardList = meetingService.contentTitleSearchMeetingList(search, pageable);

            // 이미지 유무 체크
            List<MeetingBoard> imgboardList = meetingService.getMeetingBoardImgList(contentTitleSearchBoardList);
            List<Integer> imageInfo = meetingService.getImageInfo(imgboardList);

            // 이미지 유무 체크 정보
            model.addAttribute("imageInfo", imageInfo);
            // 검색에 맞는 게시글 정보
            model.addAttribute("boardList", contentTitleSearchBoardList);
            // 검색 조건 내용
            model.addAttribute("searchOption", searchOption);
            // 검색 내용
            model.addAttribute("search", search);

            return "meeting/meetingSearchMain";

        } else if (searchOption == 3){
            // 검색에 맞는 게시글 정보
            Page<MeetingBoard> writerSearchBoardList = meetingService.writerSearchMeetingList(search, pageable);

            // 이미지 유무 체크
            List<MeetingBoard> imgboardList = meetingService.getMeetingBoardImgList(writerSearchBoardList);
            List<Integer> imageInfo = meetingService.getImageInfo(imgboardList);

            // 이미지 유무 체크 정보
            model.addAttribute("imageInfo", imageInfo);
            // 검색에 맞는 게시글 정보
            model.addAttribute("boardList", writerSearchBoardList);
            // 검색 조건 내용
            model.addAttribute("searchOption", searchOption);
            // 검색 내용
            model.addAttribute("search", search);

            return "meeting/meetingSearchMain";


        } else {
            //오류
            return "redirect:/meeting/main";
        }
    }

}
