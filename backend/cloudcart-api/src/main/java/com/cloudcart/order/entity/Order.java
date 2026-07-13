package com.cloudcart.order.entity;

import com.cloudcart.order.entity.enums.OrderStatus;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    private Long id;

    private UUID orderId;

    private UUID customerId;

    private OrderStatus status;

    private Instant createdAt;

    private Instant updatedAt;
}
