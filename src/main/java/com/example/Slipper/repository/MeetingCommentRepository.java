package com.example.Slipper.repository;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MeetingCommentRepository extends JpaRepository<MeetingBoardComment, Integer> {
    // 댓글 정보
    @Query(value = "SELECT * \n" +
            "FROM meetingBoardComment\n" +
            "WHERE meetNum = :meetNum" , nativeQuery = true)
    Page<MeetingBoardComment> commentList(@Param("meetNum") int meetNum, Pageable pageable);

    // 해당 게시글 번호에 해당하는 댓글 데이터 삭제
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM meetingBoardComment \n" +
            "WHERE meetNum = :meetNum", nativeQuery = true)
    void meetCommentDelete(@Param("meetNum")Integer meetNum);
}
