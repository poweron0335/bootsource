package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, String> {

    // sql 이 아님(객체를 기준으로 작성해야 함)
    // TeamMember 를 m.team 이라는 별칭으로 하고 m.team 을 t 라는 별칭으로
    // m : TeamMember 의 전체를 의미함
    // t : TeamMember 안에 team 을 의미함
    @Query("select m, t from TeamMember m join m.team t where t.name=?1")
    public List<TeamMember> findByMemberEqualTeam(String teamName);
}
