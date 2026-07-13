package com.cloudcart.order.dto.response;

import java.util.UUID;

public record CreateOrderResponse(
        UUID orderId,
        String status
) {}
