package com.example.mart.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
