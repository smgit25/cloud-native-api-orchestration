package com.cloudcart.order.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

import java.util.List;


public record CreateOrderRequest(

        @NotNull(message = "Customer ID is required")
        UUID customerId,

        @NotEmpty(message = "Order must contain at least one item")
        @Valid
        List<OrderItemRequest> items

) {}