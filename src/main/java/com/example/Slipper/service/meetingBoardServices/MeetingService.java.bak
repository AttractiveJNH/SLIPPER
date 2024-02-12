package com.example.Slipper.service.meetingBoardServices;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.repository.MeetingBoardRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MeetingService {
    @Autowired
    private MeetingBoardRepository meetingBoardRepository;

    // 지역 값 반환
    public String mapRegionToName(int region) {
        switch(region) {
            case 1:
                return "중구";
            case 2:
                return "서구";
            case 3:
                return "동구";
            case 4:
                return "영도구";
            case 5:
                return "부산진구";
            case 6:
                return "동래구";
            case 7:
                return "남구";
            case 8:
                return "북구";
            case 9:
                return "해운대구";
            case 10:
                return "사하구";
            case 11:
                return "금정구";
            case 12:
                return "강서구";
            case 13:
                return "연제구";
            case 14:
                return "수영구";
            case 15:
                return "사상구";
            case 16:
                return "기장군";
            default:
                return "Unknown";
        }
    }


    // 이미지 태그 여부 확인
    public List<Integer> getImageInfo(List<MeetingBoard> meetingBoardList) {
        List<Integer> imageInfo = new ArrayList<>();
        for (MeetingBoard board : meetingBoardList) {
            String meetContent = board.getMeet_content();
            int imageTag = checkImageTag(meetContent);
            imageInfo.add(imageTag);
        }
        return imageInfo;
    }


    // 스마트 에디터로 저장한 내용에 이미지 태그 유무 체크 메서드
    public int checkImageTag(String content) {
        Document doc = Jsoup.parse(content);
        Elements imgTags = doc.select("img");
        return imgTags.isEmpty() ? 2 : 1;
    }

    // 페이징 서비스
    public Page<MeetingBoard> getMeetingBoardList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "meetNum"));// <- Sort 추가

        return meetingBoardRepository.findAll(pageable);
    }

    // 이미지 체크용
    public List<MeetingBoard> getMeetingBoardImgList(Page<MeetingBoard> pageResult) {
        // 페이지의 내용을 리스트로 반환
        return pageResult.getContent();
    }

    // 지역 설정 페이징
    public Page<MeetingBoard> regionMeetingBoardList(String regionName, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "meetNum"));// <- Sort 추가

        return meetingBoardRepository.regionFindList(regionName, pageable);
    }

    // 카테고리 설정 페이징
    public Page<MeetingBoard> categoryMeetingBoardList(int category, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "meetNum"));// <- Sort 추가

        return meetingBoardRepository.categoryFindList(category, pageable);
    }

    // 지역 및 카테고리 설정 페이징
    public Page<MeetingBoard> sortingMeetingBoardList(String regionName, int category, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "meetNum"));// <- Sort 추가

        return meetingBoardRepository.sortingFindList(regionName, category, pageable);
    }

    // 검색 (제목만) 페이징
    public Page<MeetingBoard> titleSearchMeetingList(String title, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "meetNum"));// <- Sort 추가

        return meetingBoardRepository.titleSearch(title, pageable);
    }

    // 검색 (제목 + 내용) 페이징
    public Page<MeetingBoard> contentTitleSearchMeetingList(String search, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "meetNum"));// <- Sort 추가

        return meetingBoardRepository.contentTitleSearch(search, pageable);
    }

    // 검색 (글쓴이) 페이징
    public Page<MeetingBoard> writerSearchMeetingList(String search, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "meetNum"));// <- Sort 추가

        return meetingBoardRepository.writerSearch(search, pageable);
    }


}
