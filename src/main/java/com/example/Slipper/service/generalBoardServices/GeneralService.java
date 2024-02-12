package com.example.Slipper.service.generalBoardServices;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardComment;
import com.example.Slipper.entity.generalEntity.GeneralBoard;
import com.example.Slipper.entity.generalEntity.GeneralBoardComment;
import com.example.Slipper.repository.generalRepository.GeneralBoardRepository;
import com.example.Slipper.repository.generalRepository.GeneralCommentRepository;
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
public class GeneralService {
    @Autowired
    private GeneralBoardRepository generalBoardRepository;

    @Autowired
    private GeneralCommentRepository generalCommentRepository;

    // 페이징 서비스
    public Page<GeneralBoard> getGeneralBoardList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "generalNum"));// <- Sort 추가

        return generalBoardRepository.findAll(pageable);
    }

    // 이미지 체크용
    public List<GeneralBoard> getGeneralBoardImgList(Page<GeneralBoard> pageResult) {
        // 페이지의 내용을 리스트로 반환
        return pageResult.getContent();
    }

    // 이미지 태그 여부 확인
    public List<Integer> getImageInfo(List<GeneralBoard> generalBoardList) {
        List<Integer> imageInfo = new ArrayList<>();
        for (GeneralBoard board : generalBoardList) {
            String generalContent = board.getGeneral_content();
            int imageTag = checkImageTag(generalContent);
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

    // 댓글 페이징
    public Page<GeneralBoardComment> commentGeneralBoardList(int generalNum, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "generalCommentNum"));// <- Sort 추가

        return generalCommentRepository.generalCommentList(generalNum, pageable);
    }

    // 카테고리 설정 페이징
    public Page<GeneralBoard> categoryGeneralBoardList(int category, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "generalNum"));// <- Sort 추가

        return generalBoardRepository.generalCategoryFindList(category, pageable);
    }

    // 검색 (제목만) 페이징
    public Page<GeneralBoard> titleSearchGeneralList(String title, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "generalNum"));// <- Sort 추가

        return generalBoardRepository.generaltitleSearch(title, pageable);
    }

    // 검색 (제목 + 내용) 페이징
    public Page<GeneralBoard> contentTitleSearchGeneralList(String search, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "generalNum"));// <- Sort 추가

        return generalBoardRepository.generalContentTitleSearch(search, pageable);
    }

    // 검색 (글쓴이) 페이징
    public Page<GeneralBoard> writerSearchGeneralList(String search, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1); // page는 index 처럼 0부터 시작
        pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "generalNum"));// <- Sort 추가

        return generalBoardRepository.generalWriterSearch(search, pageable);
    }

}
