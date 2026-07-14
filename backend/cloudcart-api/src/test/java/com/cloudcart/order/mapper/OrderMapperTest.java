package com.cloudcart.order.mapper;

import com.cloudcart.order.dto.request.CreateOrderRequest;
import com.cloudcart.order.dto.request.OrderItemRequest;
import com.cloudcart.order.dto.response.CreateOrderResponse;
import com.cloudcart.order.entity.Order;
import com.cloudcart.order.entity.enums.OrderStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMapperTest {

    private final OrderMapper orderMapper = new OrderMapper();

    @Test
    void toEntity_shouldSetCustomerIdStatusAndTimestamps() {
        UUID customerId = UUID.randomUUID();
        CreateOrderRequest request = new CreateOrderRequest(
                customerId,
                List.of(
                        new OrderItemRequest(UUID.randomUUID(), 2)
                )
        );

        Instant before = Instant.now();
        Order order = orderMapper.toEntity(request);
        Instant after = Instant.now();

        assertThat(order.getOrderId()).isNotNull();
        assertThat(order.getCustomerId()).isEqualTo(customerId);
        assertThat(order.getStatus()).isEqualTo(OrderStatus.CREATED);

        assertThat(order.getCreatedAt()).isNotNull();
        assertThat(order.getUpdatedAt()).isNotNull();

        assertThat(order.getCreatedAt()).isBetween(before, after);
        assertThat(order.getUpdatedAt()).isBetween(before, after);
    }

    @Test
    void toResponse_shouldMapOrderIdAndStatusName() {
        UUID orderId = UUID.randomUUID();
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus(OrderStatus.CREATED);

        CreateOrderResponse response = orderMapper.toResponse(order);

        assertThat(response).isNotNull();
        assertThat(response.orderId()).isEqualTo(orderId);
        assertThat(response.status()).isEqualTo(OrderStatus.CREATED.name());
    }
}

