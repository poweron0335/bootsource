package com.example.mart.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.mart.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
