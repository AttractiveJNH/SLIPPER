package com.example.Slipper.service.promotionService;


import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.promotionEntity.Promotion;
import com.example.Slipper.repository.promotionRepository.PromotionRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    private PromotionRepository promotionRepository;

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

    public List<String> getAllPromotionContents() {
        List<Promotion> promotions = promotionRepository.findAll();
        return promotions.stream()
                .map(Promotion::getPromoBrdContent)
                .collect(Collectors.toList());
    }


}
