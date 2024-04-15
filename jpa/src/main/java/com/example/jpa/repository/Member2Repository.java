package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.jpa.entity.Member2;
import com.example.jpa.entity.Team2;

public interface Member2Repository extends JpaRepository<Member2, Long>, QuerydslPredicateExecutor<Member2> {

    // jpql 사용 시(정해져있음)
    // 1. entity 타입 결과 -> List<Entity명>
    // 2. 개별 타입 결과 -> List<Object[]>

    @Query("select m from Member2 m")
    List<Member2> findByMembers(Sort sort);

    @Query("select m.userName, m.age from Member2 m")
    List<Object[]> findByMembers2();

    // 특정 나이보다 많은 회원 조회
    @Query("select m from Member2 m where m.age > ?1")
    List<Member2> findbyAgeList(int age);

    // 특정 팀의 회원 조회
    @Query("select m from Member2 m where m.team2 = ?1")
    List<Member2> findByTeamEqual(Team2 team2);

    @Query("select m from Member2 m where m.team2.id = ?1")
    List<Member2> findByTeamIdEqual(Long id);

    // 집계함수
    @Query("select count(m), sum(m.age), avg(m.age), max(m.age), min(m.age) from Member2 m ")
    List<Object[]> aggregate();

    @Query("select m from Member2 m join m.team2 t where t.name = :teamName")
    List<Member2> findByTeamMeber(String teamName);

    @Query("select m, t from Member2 m join m.team2 t where t.name = :teamName")
    List<Object[]> findByTeamMeber2(String teamName);

    // 외부조인 사용시 ON 사용
    @Query("select m, t from Member2 m left join m.team2 t on t.name = :teamName")
    List<Object[]> findByTeamMeber3(String teamName);
}
