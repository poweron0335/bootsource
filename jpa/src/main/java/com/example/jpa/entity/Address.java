package com.example.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Embeddable // 다른 엔티티에서 포함할 거라는 어노테이션
@Setter
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

}
