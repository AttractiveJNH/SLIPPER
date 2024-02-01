package com.example.Slipper.repository;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardApplication;
import com.example.Slipper.entity.SswTestEntity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingApplicationRepository extends JpaRepository<MeetingBoardApplication, Integer> {
    // 일반 유저가 신청했는지 체크하는 쿼리
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meet_num = :#{#meetInfo.meet_num}\n" +
            "AND user_num = :#{#userInfo.user_num}", nativeQuery = true)
    MeetingBoardApplication userApcCheck(@Param("userInfo")Users userInfo, @Param("meetInfo") MeetingBoard meetInfo);


    // 해당 게시글 일반 신청자 수락 대기자 정보 (meet_apply_status == 0)
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meet_num = :meetNum\n" +
            "AND meet_apply_status = 0", nativeQuery = true)
    List<MeetingBoardApplication> applyWaitUserList(@Param("meetNum") Integer meetNum);


    // 해당 게시글 수락한 신청자 정보 (meet_apply_status == 1)
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meet_num = :meetNum\n" +
            "AND meet_apply_status = 1", nativeQuery = true)
    List<MeetingBoardApplication> applyAcceptUserList(@Param("meetNum") Integer meetNum);

    // 해당 게시글 거절한 신청자 정보 (meet_apply_status == 2)
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meet_num = :meetNum\n" +
            "AND meet_apply_status = 2", nativeQuery = true)
    List<MeetingBoardApplication> applyRefuseUserList(@Param("meetNum") Integer meetNum);


    // 모임 게시판 참가 수락
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meet_num = :meetNum\n" +
            "AND user_num = :userNum", nativeQuery = true)
    MeetingBoardApplication findApplyUser(@Param("meetNum") Integer meetNum, @Param("userNum") Integer userNum);

}
