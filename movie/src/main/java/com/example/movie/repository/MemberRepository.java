package com.example.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movie.entity.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 1) findBy ~ 로 생성하면 처리가능
    // 2) findBy + @Query
    // 3) JPQL

    Optional<Member> findByEmail(String email);
}
