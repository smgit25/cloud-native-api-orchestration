package com.cloudcart.order.repository;

import com.cloudcart.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    //Optional<Order> findByOrderId(UUID orderId);
}
