package com.example.Slipper.repository.promotionRepository;

import com.example.Slipper.entity.promotionEntity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    ArrayList<Promotion> findAll();

    // 지역과 카테고리를 선택한 경우에 조건에 맞는 promotion_board의 데이터를 가져오는 메소드 정의.
    List<Promotion> findByPromoBrdRegionAndPromoBrdCategory(
            @Param("promoBrdRegion") String promoBrdRegion,
            @Param("promoBrdCategory") int promoBrdCategory);

    List<Promotion> findByPromoBrdRegion(@Param("promoBrdRegion") String promoBrdRegion);
    List<Promotion> findByPromoBrdCategory(@Param("promoBrdCategory") int promoBrdCategory);

    Promotion findByPromoBrdPostId(@Param("promoBrdPostId") int promoBrdPostId);

    @Query(value = "SELECT * FROM promotion p " +
            "WHERE (:promoBrdRegion IS NULL OR p.promoBrdRegion = :promoBrdRegion) " +
            "AND (:promoBrdCategory IS NULL OR p.promoBrdCategory = :promoBrdCategory)",
            nativeQuery = true)
    List<Promotion> findByRegionAndCategory(@Param("promoBrdRegion") String promoBrdRegion,
                                            @Param("promoBrdCategory") Integer promoBrdCategory);
}
