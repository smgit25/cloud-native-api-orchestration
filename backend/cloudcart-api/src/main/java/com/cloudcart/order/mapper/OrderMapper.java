package com.cloudcart.order.mapper;

import com.cloudcart.order.dto.request.CreateOrderRequest;
import com.cloudcart.order.dto.request.OrderItemRequest;
import com.cloudcart.order.dto.response.CreateOrderResponse;
import com.cloudcart.order.entity.Order;
import com.cloudcart.order.entity.OrderItem;
import com.cloudcart.order.entity.enums.OrderStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class OrderMapper {

    public Order toEntity(CreateOrderRequest request) {

        Instant now = Instant.now();

        Order order = new Order();

        // Order Details
        order.setOrderId(UUID.randomUUID());
        order.setCustomerId(request.customerId());
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(now);
        order.setUpdatedAt(now);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        // Create Order Items
        for (OrderItemRequest itemRequest : request.items()) {

            OrderItem item = new OrderItem();

            item.setProductId(itemRequest.productId());
            item.setQuantity(itemRequest.quantity());

            // Temporary price
            BigDecimal unitPrice = BigDecimal.valueOf(1000);

            item.setUnitPrice(unitPrice);

            // IMPORTANT - Establish relationship
            item.setOrder(order);

            orderItems.add(item);

            // Calculate Total
            totalAmount = totalAmount.add(
                    unitPrice.multiply(
                            BigDecimal.valueOf(itemRequest.quantity())
                    )
            );
        }

        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);

        return order;
    }

    public CreateOrderResponse toResponse(Order order) {

        return new CreateOrderResponse(
                order.getOrderId(),
                order.getStatus().name()
        );
    }
}