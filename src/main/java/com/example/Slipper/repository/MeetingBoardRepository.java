package com.example.Slipper.repository;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MeetingBoardRepository extends JpaRepository<MeetingBoard, Integer> {
    // 지역만 선택했을 때 게시글 정보
    @Query(value = "SELECT * \n" +
            "FROM meetingBoard \n" +
            "WHERE meet_field = :meetField", nativeQuery = true)
    Page<MeetingBoard> regionFindList(@Param("meetField") String regionName, Pageable pageable);

    // 카테고리만 선택했을 때 게시글 정보
    @Query(value = "SELECT * \n" +
            "FROM meetingBoard \n" +
            "WHERE meet_category = :meetCategory", nativeQuery = true)
    Page<MeetingBoard> categoryFindList(@Param("meetCategory") int meetCategory, Pageable pageable);

    // 지역과 카테고리 선택했을 때 게시글 정보
    @Query(value = "SELECT * \n" +
            "FROM meetingBoard\n" +
            "WHERE meet_field = :meetField \n" +
            "AND meet_category = :meetCategory", nativeQuery = true)
    Page<MeetingBoard> sortingFindList(@Param("meetField") String regionName, @Param("meetCategory") int meetCategory, Pageable pageable);

    // 검색 (제목만)
    @Query(value = "SELECT * \n" +
            "FROM meetingBoard \n" +
            "WHERE meet_title \n" +
            "LIKE  %:title%", nativeQuery = true)
    Page<MeetingBoard> titleSearch(@Param("title") String title, Pageable pageable);

    // 검색 (제목+내용)
    @Query(value = "SELECT * \n" +
            "FROM meetingBoard \n" +
            "WHERE meet_title \n" +
            "LIKE  %:search% \n" +
            "OR meet_content \n" +
            "LIKE %:search%", nativeQuery = true)
    Page<MeetingBoard> contentTitleSearch(@Param("search") String search, Pageable pageable);

    // 검색 (글쓴이)
    @Query(value = "SELECT * \n" +
            "FROM meetingBoard \n" +
            "WHERE meet_nick_name \n" +
            "LIKE  %:writer%", nativeQuery = true)
    Page<MeetingBoard> writerSearch(@Param("writer") String writer, Pageable pageable);





}
