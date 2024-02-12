package com.example.Slipper.repository;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MeetingApplicationRepository extends JpaRepository<MeetingBoardApplication, Integer> {
    // 일반 유저가 신청했는지 체크하는 쿼리
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meetNum = :#{#meetInfo.meetNum}\n" +
            "AND user_num = :userInfo", nativeQuery = true)
    MeetingBoardApplication userApcCheck(@Param("userInfo") Long userInfo, @Param("meetInfo") MeetingBoard meetInfo);

    // 사업자 유저가 신청했는지 체크하는 쿼리
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meetNum = :#{#meetInfo.meetNum}\n" +
            "AND entrepre_num = :entreInfo", nativeQuery = true)
    MeetingBoardApplication entreApcCheck(@Param("entreInfo") Long entreInfo, @Param("meetInfo") MeetingBoard meetInfo);


    // 해당 게시글 일반 신청자 수락 대기자 정보 (meet_apply_status == 0)
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meetNum = :meetNum\n" +
            "AND meet_apply_status = 0", nativeQuery = true)
    List<MeetingBoardApplication> applyWaitUserList(@Param("meetNum") Integer meetNum);


    // 해당 게시글 수락한 신청자 정보 (meet_apply_status == 1)
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meetNum = :meetNum\n" +
            "AND meet_apply_status = 1", nativeQuery = true)
    List<MeetingBoardApplication> applyAcceptUserList(@Param("meetNum") Integer meetNum);

    // 해당 게시글 거절한 신청자 정보 (meet_apply_status == 2)
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meetNum = :meetNum\n" +
            "AND meet_apply_status = 2", nativeQuery = true)
    List<MeetingBoardApplication> applyRefuseUserList(@Param("meetNum") Integer meetNum);


    // 모임 게시판 참가자 관리 (일반 유저 정보에 맞는 신청 정보)
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meetNum = :meetNum\n" +
            "AND user_num = :userNum", nativeQuery = true)
    MeetingBoardApplication findApplyUser(@Param("meetNum") Integer meetNum, @Param("userNum") Long userNum);

    // 모임 게시판 참가자 관리 (사업자 유저 정보에 맞는 신청 정보)
    @Query(value = "SELECT *\n" +
            "FROM meetingBoardApplication\n" +
            "WHERE meetNum = :meetNum\n" +
            "AND entrepre_num = :entreNum", nativeQuery = true)
    MeetingBoardApplication findApplyEntre(@Param("meetNum") Integer meetNum, @Param("entreNum") Long entreNum);

    // 해당 게시글 번호에 해당하는 데이터 삭제
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM meetingBoardApplication \n" +
            "WHERE meetNum = :meetNum", nativeQuery = true)
    void deleteMeetApc(@Param("meetNum")Integer meetNum);

}
