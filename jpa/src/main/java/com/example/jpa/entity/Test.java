package com.example.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

// create table test (
//     id number(19,0) not null,
//     id2 number(19,0) not null,
//     id3 number(10,0) not null,
//     id4 number(10,0),
//     primary key (id)
// )

// 기본 : int, long, boolean, char, float, double : null 대입 불가
// 객체 : Integer, Long, Boolean, .... etc : null 대입 가능

@Entity
public class Test {

    @Id
    private Long id;
    @Column(columnDefinition = "number(8)")
    private long id2;
    private int id3;
    private Integer id4;
}
