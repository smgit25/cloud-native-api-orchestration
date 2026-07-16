package com.cloudcart.order.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record GetOrderSummaryResponse(

    UUID orderId,

    UUID customerId,

    String status,

    Instant createdAt,

    BigDecimal totalAmount
){}
