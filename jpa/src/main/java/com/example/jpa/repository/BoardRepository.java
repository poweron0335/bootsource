package com.example.jpa.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.Board;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // id 로 찾기(findById), 전체 찾기(findAll())

    @Query(value = "select * from boardtbl", nativeQuery = true)
    List<Board> findList();

    // title 로 찾기
    @Query("select b from Board b where b.title like %?1%")
    List<Board> findByTitle(String title);

    // writer 로 찾기
    @Query("select b from Board b where b.writer like %:writer%")
    List<Board> findByWriter(String writer);

    // like
    List<Board> findByTitleLike(String title);

    List<Board> findByTitleStartingWith(String title);

    List<Board> findByTitleEndingWith(String title);

    List<Board> findByTitleContaining(String title);

    // writer 가 user 시작하는 작성자 찾기
    List<Board> findByWriterStartingWith(String writer);

    // title 이 title 문자열이 포함되어 있거나
    // content 가 Content 문자열이 포함되어 있는 데이터 조회
    @Query("select b from Board b where b.title like %?1% or b.content = ?2")
    List<Board> findByTitleContainingOrContent(String title, String Content);

    List<Board> findByTitleContainingOrContentContaining(String title, String Content);

    // title 이 Title 문자열이 포함되어 있고, id 가 50 보다 큰 게시물 조회
    @Query("select b from Board b where b.title like %?1% and b.id > ?2")
    List<Board> findByTitleContainingAndIdGreaterThan(String title, Long id);

    // id 가 50 보다 큰 게시물 조회 시 내림차순 정렬
    List<Board> findByIdGreaterThanOrderByIdDesc(Long id);

    List<Board> findByIdGreaterThanOrderByIdDesc(Long id, Pageable pageable);

}
