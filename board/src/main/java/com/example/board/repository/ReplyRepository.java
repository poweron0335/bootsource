package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // bno 삭제
    // @Query("delete from Reply r where r.board.bno = ?1")
    @Modifying // delete, update 구문 작성시 같이 사용(기본적으로 select 전용이기 때문에)
    @Query("delete from Reply r where r.board.bno =:bno")
    void deleteByBno(Long bno);
}
