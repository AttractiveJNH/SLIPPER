package com.example.Slipper.repository.generalRepository;

import com.example.Slipper.entity.MeetingBoardEntity.MeetingBoard;
import com.example.Slipper.entity.generalEntity.GeneralBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface GeneralBoardRepository extends JpaRepository<GeneralBoard, Integer> {

    // 일반 게시판 해당 게시글 사업자 기본키 찾기
    @Query(value = "SELECT entrepre_num \n" +
            "FROM generalboard \n" +
            "WHERE generalNum =:generalNum" , nativeQuery = true)
    Long generalEntreWriter(@Param("generalNum") int generalNum);


    // 일반 게시판 해당 게시글 일반 유저 기본키 찾기
    @Query(value = "SELECT user_num \n" +
            "FROM generalboard \n" +
            "WHERE generalNum =:generalNum" , nativeQuery = true)
    Long generalUserWriter(@Param("generalNum") int generalNum);

    // 카테고리만 선택했을 때 게시글 정보
    @Query(value = "SELECT * \n" +
            "FROM generalboard \n" +
            "WHERE general_category = :general_category", nativeQuery = true)
    Page<GeneralBoard> generalCategoryFindList(@Param("general_category") int general_category, Pageable pageable);

    // 검색 (제목만)
    @Query(value = "SELECT * \n" +
            "FROM generalboard \n" +
            "WHERE general_title \n" +
            "LIKE  %:title%", nativeQuery = true)
    Page<GeneralBoard> generaltitleSearch(@Param("title") String title, Pageable pageable);

    // 검색 (제목+내용)
    @Query(value = "SELECT * \n" +
            "FROM generalboard \n" +
            "WHERE general_title \n" +
            "LIKE  %:search% \n" +
            "OR general_content \n" +
            "LIKE %:search%", nativeQuery = true)
    Page<GeneralBoard> generalContentTitleSearch(@Param("search") String search, Pageable pageable);

    // 검색 (글쓴이)
    @Query(value = "SELECT * \n" +
            "FROM generalboard \n" +
            "WHERE general_nick_name \n" +
            "LIKE  %:writer%", nativeQuery = true)
    Page<GeneralBoard> generalWriterSearch(@Param("writer") String writer, Pageable pageable);

    // 일반 게시판 해당 게시글 사업자 기본키 찾기
    @Query(value = "SELECT entrepre_num \n" +
            "FROM generalboard \n" +
            "WHERE generalNum =:generalNum" , nativeQuery = true)
    Long generalEntreWriterFind(@Param("generalNum") int generalNum);


    // 일반 게시판 해당 게시글 일반 유저 기본키 찾기
    @Query(value = "SELECT user_num \n" +
            "FROM generalboard \n" +
            "WHERE generalNum =:generalNum" , nativeQuery = true)
    Long generalUserWriterFind(@Param("generalNum") int generalNum);
}
