package com.kaanaydemir.orderservice.repository;

import com.kaanaydemir.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
