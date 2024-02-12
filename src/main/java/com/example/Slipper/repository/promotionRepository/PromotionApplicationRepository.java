//package com.example.Slipper.repository.promotionRepository;
//
//
//import com.example.Slipper.entity.SswTestEntity.Users;
//import com.example.Slipper.entity.promotionEntity.PromotionBoard;
//import com.example.Slipper.entity.promotionEntity.PromotionBoardApplication;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.List;
//
//public interface PromotionApplicationRepository extends JpaRepository<PromotionBoardApplication, Integer> {
//
//    // 일반 유저가 신청했는지 체크하는 쿼리
//    @Query(value = "SELECT *\n" +
//            "FROM promotionBoardApplication\n" +
//            "WHERE promoBrdPostId = :#{#promotionInfo.promoBrdPostId}\n" +
//            "AND user_num = :#{#userInfo.user_num}", nativeQuery = true)
//    PromotionBoardApplication userApcCheck(@Param("userInfo") Users userInfo, @Param("promotionInfo") PromotionBoard promotionInfo);
//
//
//    // 해당 게시글 일반 신청자 수락 대기자 정보 (meet_apply_status == 0)
//    @Query(value = "SELECT *\n" +
//            "FROM promotionBoardApplication\n" +
//            "WHERE promoBrdPostId = :promoBrdPostId\n" +
//            "AND promotion_apply_status = 0", nativeQuery = true)
//    List<PromotionBoardApplication> applyWaitUserList(@Param("promoBrdPostId") Integer promoBrdPostId);
//
//
//    // 해당 게시글 수락한 신청자 정보 (meet_apply_status == 1)
//    @Query(value = "SELECT *\n" +
//            "FROM promotionBoardApplication\n" +
//            "WHERE promoBrdPostId = :promoBrdPostId\n" +
//            "AND promotion_apply_status = 1", nativeQuery = true)
//    List<PromotionBoardApplication> applyAcceptUserList(@Param("promoBrdPostId") Integer promoBrdPostId);
//
//    // 해당 게시글 거절한 신청자 정보 (meet_apply_status == 2)
//    @Query(value = "SELECT *\n" +
//            "FROM promotionBoardApplication\n" +
//            "WHERE promoBrdPostId = :promoBrdPostId\n" +
//            "AND promotion_apply_status = 2", nativeQuery = true)
//    List<PromotionBoardApplication> applyRefuseUserList(@Param("promoBrdPostId") Integer promoBrdPostId);
//
//
//    // 모임 게시판 참가 수락
//    @Query(value = "SELECT *\n" +
//            "FROM promotionBoardApplication\n" +
//            "WHERE promoBrdPostId = :promoBrdPostId\n" +
//            "AND user_num = :userNum", nativeQuery = true)
//    PromotionBoardApplication findApplyUser(@Param("promoBrdPostId") Integer promoBrdPostId, @Param("userNum") Integer userNum);
//}
