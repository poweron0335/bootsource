package com.example.jpa.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = { "members" }) // ToString 생성시 클래스 내 모든 property 가 기준임
@Getter
@Setter
@Entity
public class Team {

    @Column(name = "team_id")
    @Id
    private String id;

    @Column(name = "team_name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER) // 사용할 때 주인이 누구인지 알려줘야함
    // 다(N) 쪽에 해당하는 건 무조건 List 로 갖고오기
    // 팀을 기준으로 멤버를 조회하겠음
    private List<TeamMember> members = new ArrayList<>();

}
