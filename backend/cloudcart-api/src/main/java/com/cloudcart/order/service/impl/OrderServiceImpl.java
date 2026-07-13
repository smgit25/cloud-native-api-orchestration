package com.cloudcart.order.service.impl;

import com.cloudcart.order.dto.request.CreateOrderRequest;
import com.cloudcart.order.dto.response.CreateOrderResponse;
import com.cloudcart.order.entity.Order;
import com.cloudcart.order.entity.enums.OrderStatus;
import com.cloudcart.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log =
            LoggerFactory.getLogger(OrderServiceImpl.class);


    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        Order order = new Order();
        order.setOrderId(UUID.randomUUID());
        order.setCustomerId(request.customerId());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());

        log.info("Created Order : {}", order);

        return new CreateOrderResponse(
                order.getOrderId(),
                order.getStatus().name()
        );
    }
}
