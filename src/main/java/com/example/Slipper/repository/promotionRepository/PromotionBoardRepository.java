package com.example.Slipper.repository.promotionRepository;

import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PromotionBoardRepository extends JpaRepository<PromotionBoard, Integer> {

    ArrayList<PromotionBoard> findAll();

    // 지역과 카테고리를 선택한 경우에 조건에 맞는 promotion_board의 데이터를 가져오는 메소드 정의.
    Page<PromotionBoard> findByPromoBrdRegionAndPromoBrdCategory(
            @Param("promoBrdRegion") String promoBrdRegion,
            @Param("promoBrdCategory") int promoBrdCategory,
            Pageable pageable);

    Page<PromotionBoard> findByPromoBrdRegion(@Param("promoBrdRegion") String promoBrdRegion, Pageable pageable);
    Page<PromotionBoard> findByPromoBrdCategory(@Param("promoBrdCategory") int promoBrdCategory, Pageable pageable);

    PromotionBoard findByPromoBrdPostId(@Param("promoBrdPostId") int promoBrdPostId);

    @Query(value = "SELECT * FROM promotion p " +
            "WHERE (:promoBrdRegion IS NULL OR p.promoBrdRegion = :promoBrdRegion) " +
            "AND (:promoBrdCategory IS NULL OR p.promoBrdCategory = :promoBrdCategory)",
            nativeQuery = true)
    ArrayList<PromotionBoard> findByRegionAndCategory(@Param("promoBrdRegion") String promoBrdRegion,
                                                      @Param("promoBrdCategory") Integer promoBrdCategory);

    Page<PromotionBoard> findAll(Pageable pageable);


    // 지역만 선택했을 때 게시글 정보
    @Query(value = "SELECT * \n" +
            "FROM promotion_board \n" +
            "WHERE promo_brd_region = :promoBrdRegion", nativeQuery = true)
    Page<PromotionBoard> regionFindList(@Param("promoBrdRegion") String promoBrdRegion, Pageable pageable);

    // 카테고리만 선택했을 때 게시글 정보
    @Query(value = "SELECT * \n" +
            "FROM promotion_board \n" +
            "WHERE promo_brd_category = :promoBrdCategory", nativeQuery = true)
    Page<PromotionBoard> categoryFindList(@Param("promoBrdCategory") int promoBrdCategory, Pageable pageable);

    // 지역과 카테고리 선택했을 때 게시글 정보
    @Query(value = "SELECT * \n" +
            "FROM promotion_board \n" +
            "WHERE promo_brd_region = :promoBrdRegion \n" +
            "AND promo_brd_category = :promoBrdCategory", nativeQuery = true)
    Page<PromotionBoard> sortingFindList(@Param("promoBrdRegion") String promoBrdRegion, @Param("promoBrdCategory") int promoBrdCategory, Pageable pageable);

    // 검색 (제목만)
    @Query(value = "SELECT * \n" +
            "FROM promotion_board \n" +
            "WHERE promo_brd_title \n" +
            "LIKE  %:title%", nativeQuery = true)
    Page<PromotionBoard> titleSearch(@Param("title") String title, Pageable pageable);

    // 검색 (제목+내용)
    @Query(value = "SELECT * \n" +
            "FROM promotion_board \n" +
            "WHERE promo_brd_title \n" +
            "LIKE  %:search% \n" +
            "OR promo_brd_content \n" +
            "LIKE %:search%", nativeQuery = true)
    Page<PromotionBoard> contentTitleSearch(@Param("search") String search, Pageable pageable);

    // 검색 (글쓴이)
//    @Query(value = "SELECT * \n" +
//            "FROM promotion_board \n" +
//            "WHERE entrepre_nick_name \n" +
//            "LIKE  %:writer%", nativeQuery = true)

    @Query(value = "SELECT pb.* " +
            "FROM promotion_board pb " +
            "JOIN entrepreneur e " +
            "ON e.entrepre_id = pb.entrepre_id " +
            "WHERE e.entrepre_id " +
            "LIKE CONCAT('%', :writer, '%')", nativeQuery =true)
    Page<PromotionBoard> writerSearch(@Param("writer") String writer, Pageable pageable); // 닉네임 검색하면 같은 닉네임 다 나올려나. 수정 필요할수도. promotionBoard테이블에 컬럼 추가해야 될 수 있지만...넘어가자.
}
