package com.example.Slipper.repository.generalRepository;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoardComment;
import com.example.Slipper.entity.generalEntity.GeneralBoardComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface GeneralCommentRepository extends JpaRepository<GeneralBoardComment, Integer> {
    // 댓글 정보
    @Query(value = "SELECT * \n" +
            "FROM generalboardcomment\n" +
            "WHERE generalNum = :generalNum" , nativeQuery = true)
    Page<GeneralBoardComment> generalCommentList(@Param("generalNum") int generalNum, Pageable pageable);

    // 해당 게시글 번호에 해당하는 댓글 데이터 삭제
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM generalboardcomment \n" +
            "WHERE generalNum = :generalNum", nativeQuery = true)
    void generalCommentDelete(@Param("generalNum")Integer generalNum);
}
