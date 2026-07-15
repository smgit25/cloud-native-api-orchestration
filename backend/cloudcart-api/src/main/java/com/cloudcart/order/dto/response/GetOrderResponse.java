package com.cloudcart.order.dto.response;

import com.cloudcart.order.entity.Order;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record GetOrderResponse(

        UUID orderId,

        UUID customerId,

        String status,

        BigDecimal totalAmount,

        Instant createdAt,

        List<OrderItemResponse> items

){}