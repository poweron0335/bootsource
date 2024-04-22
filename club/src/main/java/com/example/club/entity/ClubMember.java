package com.example.club.entity;

import java.util.HashSet;
import java.util.Set;

import com.example.club.constant.ClubMemberRole;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Setter
@Getter
@Entity
public class ClubMember extends BaseEntity {

    @Id
    private String email;

    private String password;

    private String name;

    private boolean fromSocial; // 소셜가입

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<ClubMemberRole> roleSet = new HashSet<>();

    public void addMemberRole(ClubMemberRole memberRole) {
        roleSet.add(memberRole);
    }
}
