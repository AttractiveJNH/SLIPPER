package com.example.Slipper.repository.promotionRepository;

import com.example.Slipper.entity.promotionEntity.PromotionBoardComment;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface PromotionBoardCommentRepository extends JpaRepository<PromotionBoardComment, Integer> {

    @Query(value = "SELECT pbc.* " +
            "FROM promotion_board_comment pbc " +
            "LEFT JOIN User u " +
            "ON pbc.user_num = u.user_num " +
            "WHERE pbc.promo_brd_post_id = :promoBrdPostId", nativeQuery = true)
    ArrayList<PromotionBoardComment> findByPromoBrdPostId(@Param("promoBrdPostId") int promoBrdPostId);

    PromotionBoardComment findByPromoBrdComntId(@Param("promoBrdComntId") int promoBrdComntId);

//    PromotionBoardComment findByPromoBrdComntId(@Param("promoBrdComntId") int promoBrdComntId);

//    @Query(value = "SELECT pbc, u.userNickname " +
//            "FROM PromotionBoardComment pbc " +
//            "JOIN User u ON pbc.userNum = u.userNum " +
//            "WHERE pbc.proBrdPostId = :promoBrdPostId", nativeQuery = true)
//    ArrayList<PromotionBoardComment> findAll();
}
