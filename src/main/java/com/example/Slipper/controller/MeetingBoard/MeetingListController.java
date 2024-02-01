package com.example.Slipper.controller.MeetingBoard;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.repository.MeetingBoardRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MeetingListController {
    @Autowired
    MeetingBoardRepository meetingBoardRepository;

    //모임게시판 목록 페이지
    @GetMapping("/meeting/main")
    public String meetingMainPage(Model model){
        //MeetingBoard에서 게시글 정보를 찾기
        List<MeetingBoard> meetingBoardList = (List<MeetingBoard>)meetingBoardRepository.findAll(); //lterable을 List로 다운캐스팅

        // 이미지 태그 여부 확인
        List<Integer> imageInfo = new ArrayList<>();
        for (MeetingBoard board : meetingBoardList) {
            String meetContent = board.getMeet_content();
            int imageTag = imageCheck(meetContent);
            imageInfo.add(imageTag);
        }

        //게시글 이미지 유무 정보 : 이미지 태그가 존재하면 1을 반환, 존재하지 않으면 2를 반환
        model.addAttribute("imageInfo", imageInfo);
        //게시글 정보
        model.addAttribute("meetingBdInfo", meetingBoardList);

        return "meeting/meetingMain";
    }

    // 스마트 에디터로 저장한 내용에 이미지 태그 유무 체크 메서드
    private int imageCheck(String content) {
        Document doc = Jsoup.parse(content);
        Elements imgTags = doc.select("img");
        return imgTags.isEmpty() ? 2 : 1;
    }
}
