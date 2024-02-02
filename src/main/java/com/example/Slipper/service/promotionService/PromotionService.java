package com.example.Slipper.service.promotionService;


import com.example.Slipper.entity.promotionEntity.Promotion;
import com.example.Slipper.repository.promotionRepository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    public Page<Promotion> promotionList(Pageable pageable){
        return promotionRepository.findAll(pageable);
    }


}
