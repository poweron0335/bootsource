package com.example.mart.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
