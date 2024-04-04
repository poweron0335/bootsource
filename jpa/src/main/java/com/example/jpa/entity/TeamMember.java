package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class TeamMember {

    @Column(name = "member_id")
    @Id
    private String id;

    private String userName;

    // sql 코드 외래키 제약조건과 같은 구문
    // Many(TeamMember)ToOne(Team) => N:1 (다대일)
    @ManyToOne
    private Team team;
}
