package com.example.Slipper.util;

import com.example.Slipper.service.promotionService.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ImageUrlParser {

    @Autowired
    private PromotionService promotionService;

    public List<String> extractImageUrlsFromContents() {
        List<String> contents = promotionService.getAllPromotionContents();
        List<String> imageUrls = new ArrayList<>();

        // 각 데이터에서 이미지 URL을 추출하여 리스트에 추가
        for (String content : contents) {
            String imageUrl = extractImageUrl(content);
            if (imageUrl != null && !imageUrl.isEmpty()) {
                imageUrls.add(imageUrl);
            }
        }

        return imageUrls;
    }

    private String extractImageUrl(String content) {
        String imageUrl = null;

        // 이미지 태그의 src 속성을 추출하기 위한 정규 표현식
        String regex = "<img\\s+src=\"(.*?)\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        // 정규 표현식과 일치하는 첫 번째 그룹을 찾음
        if (matcher.find()) {
            imageUrl = matcher.group(1);
        }

        return imageUrl;
    }
}