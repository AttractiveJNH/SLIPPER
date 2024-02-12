package com.example.Slipper.repository.reviewRepository;

import com.example.Slipper.entity.promotionEntity.PromotionBoardComment;
import com.example.Slipper.entity.reviewEntity.ReviewBoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ReviewBoardCommentRepository extends JpaRepository<ReviewBoardComment, Integer> {

    ArrayList<ReviewBoardComment> findByRevBrdPostId(@Param("revBrdPostId") int revBrdPostId);

    ReviewBoardComment findByRevBrdComntId(@Param("revBrdComntId") int revBrdComntId);
}
