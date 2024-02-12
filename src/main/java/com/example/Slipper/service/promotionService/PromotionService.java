package com.example.Slipper.service.promotionService;



import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import com.example.Slipper.repository.promotionRepository.PromotionBoardRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionBoardRepository promotionBoardRepository;

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
    public List<Integer> getImageInfo(List<PromotionBoard> promotionBoardList) {
        List<Integer> imageInfo = new ArrayList<>();
        for (PromotionBoard board : promotionBoardList) {
            String promotionContent = board.getPromoBrdContent();
            int imageTag = checkImageTag(promotionContent);
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
    public Page<PromotionBoard> getPromotionBoardList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "promoBrdPostId"));// <- Sort 추가

        return promotionBoardRepository.findAll(pageable);
    }

    // 이미지 체크용
    public List<PromotionBoard> getPromotionBoardImgList(Page<PromotionBoard> pageResult) {
        // 페이지의 내용을 리스트로 반환
        return pageResult.getContent();
    }

    // 지역 설정 페이징
    public Page<PromotionBoard> regionPromotionBoardList(String regionName, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "promoBrdPostId"));// <- Sort 추가

        return promotionBoardRepository.regionFindList(regionName, pageable);
    }

    // 카테고리 설정 페이징
    public Page<PromotionBoard> categoryPromotionBoardList(int category, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "promoBrdPostId"));// <- Sort 추가

        return promotionBoardRepository.categoryFindList(category, pageable);
    }

    // 지역 및 카테고리 설정 페이징
    public Page<PromotionBoard> sortingPromotionBoardList(String regionName, int category, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "promoBrdPostId"));// <- Sort 추가

        return promotionBoardRepository.sortingFindList(regionName, category, pageable);
    }

    // 검색 (제목만) 페이징
    public Page<PromotionBoard> titleSearchPromotionList(String title, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "promoBrdPostId"));// <- Sort 추가

        return promotionBoardRepository.titleSearch(title, pageable);
    }

    // 검색 (제목 + 내용) 페이징
    public Page<PromotionBoard> contentTitleSearchPromotionList(String search, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "promoBrdPostId"));// <- Sort 추가

        return promotionBoardRepository.contentTitleSearch(search, pageable);
    }

    // 검색 (글쓴이) 페이징
    public Page<PromotionBoard> writerSearchPromotionList(String search, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "promoBrdPostId"));// <- Sort 추가

        return promotionBoardRepository.writerSearch(search, pageable);
    }



}
