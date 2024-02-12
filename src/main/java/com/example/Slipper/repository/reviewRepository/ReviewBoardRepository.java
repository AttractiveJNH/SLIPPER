package com.example.Slipper.repository.reviewRepository;

import com.example.Slipper.entity.promotionEntity.PromotionBoard;
import com.example.Slipper.entity.reviewEntity.ReviewBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Integer> {

    ArrayList<ReviewBoard> findAll();

    // 지역과 카테고리를 선택한 경우에 조건에 맞는 promotion_board의 데이터를 가져오는 메소드 정의.
    Page<ReviewBoard> findByRevBrdRegionAndRevBrdCategory(
            @Param("revBrdRegion") String revBrdRegion,
            @Param("revBrdCategory") int revBrdCategory,
            Pageable pageable);

    Page<ReviewBoard> findByRevBrdRegion(@Param("revBrdRegion") String revBrdRegion, Pageable pageable);
    Page<ReviewBoard> findByRevBrdCategory(@Param("revBrdCategory") int revBrdCategory, Pageable pageable);

    ReviewBoard findByRevBrdPostId(@Param("revBrdPostId") int revBrdPostId);

    @Query(value = "SELECT * FROM review_board rb " +
            "WHERE (:revBrdRegion IS NULL OR rb.revBrdRegion = :revBrdRegion) " +
            "AND (:revBrdCategory IS NULL OR rb.revBrdCategory = :revBrdCategory)",
            nativeQuery = true)
    ArrayList<ReviewBoard> findByRegionAndCategory(@Param("revBrdRegion") String revBrdRegion,
                                                      @Param("revBrdCategory") Integer revBrdCategory);

    Page<ReviewBoard> findAll(Pageable pageable);


    // 지역만 선택했을 때 게시글 정보
    @Query(value = "SELECT * \n" +
            "FROM review_board \n" +
            "WHERE rev_brd_region = :revBrdRegion", nativeQuery = true)
    Page<ReviewBoard> regionFindList(@Param("revBrdRegion") String revBrdRegion, Pageable pageable);

    // 카테고리만 선택했을 때 게시글 정보
    @Query(value = "SELECT * \n" +
            "FROM review_board \n" +
            "WHERE rev_brd_category = :revBrdCategory", nativeQuery = true)
    Page<ReviewBoard> categoryFindList(@Param("revBrdCategory") int revBrdCategory, Pageable pageable);

    // 지역과 카테고리 선택했을 때 게시글 정보
    @Query(value = "SELECT * \n" +
            "FROM review_board \n" +
            "WHERE rev_brd_region = :revBrdRegion \n" +
            "AND rev_brd_category = :revBrdCategory", nativeQuery = true)
    Page<ReviewBoard> sortingFindList(@Param("revBrdRegion") String revBrdRegion, @Param("revBrdCategory") int revBrdCategory, Pageable pageable);

    // 검색 (제목만)
    @Query(value = "SELECT * \n" +
            "FROM review_board \n" +
            "WHERE rev_brd_title \n" +
            "LIKE  %:title%", nativeQuery = true)
    Page<ReviewBoard> titleSearch(@Param("title") String title, Pageable pageable);

    // 검색 (제목+내용)
    @Query(value = "SELECT * \n" +
            "FROM review_board \n" +
            "WHERE rev_brd_title \n" +
            "LIKE  %:search% \n" +
            "OR rev_brd_content \n" +
            "LIKE %:search%", nativeQuery = true)
    Page<ReviewBoard> contentTitleSearch(@Param("search") String search, Pageable pageable);

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
    Page<ReviewBoard> writerSearch(@Param("writer") String writer, Pageable pageable); // 닉네임 검색하면 같은 닉네임 다 나올려나. 수정 필요할수도. promotionBoard테이블에 컬럼 추가해야 될 수 있지만...넘어가자.
}
