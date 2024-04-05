package com.example.mart.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
