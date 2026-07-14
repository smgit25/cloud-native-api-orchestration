package com.cloudcart.order.mapper;

import com.cloudcart.order.dto.request.CreateOrderRequest;
import com.cloudcart.order.dto.response.CreateOrderResponse;
import com.cloudcart.order.entity.Order;
import com.cloudcart.order.entity.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class OrderMapper {

    public Order toEntity(CreateOrderRequest request){

        Instant now = Instant.now();

        Order order = new Order();

        order.setOrderId(UUID.randomUUID());
        order.setCustomerId(request.customerId());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        return order;
    }

    public CreateOrderResponse toResponse(Order order) {

        return new CreateOrderResponse(
                order.getOrderId(),
                order.getStatus().name()
        );
    }

}
