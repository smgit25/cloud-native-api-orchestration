package com.cloudcart.order.service.impl;

import com.cloudcart.order.dto.request.CreateOrderRequest;
import com.cloudcart.order.dto.response.CreateOrderResponse;
import com.cloudcart.order.dto.response.GetOrderResponse;
import com.cloudcart.order.dto.response.GetOrderSummaryResponse;
import com.cloudcart.order.entity.Order;
import com.cloudcart.order.mapper.OrderMapper;
import com.cloudcart.order.repository.OrderRepository;
import com.cloudcart.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger log =
            LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    private OrderServiceImpl(OrderMapper orderMapper, OrderRepository orderRepository){
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }


    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        Order order = orderMapper.toEntity(request);

        log.info("Created Order : {}", order);

        log.info(
                "Saving Order -> id={}, orderId={}, customerId={}, status={}",
                order.getId(),
                order.getOrderId(),
                order.getCustomerId(),
                order.getStatus()
        );

        Order savedOrder = orderRepository.save(order);

        log.info("Saved order : {}", savedOrder);

        return orderMapper.toResponse(order);

    }


    @Override
    public GetOrderResponse getOrder(UUID orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return orderMapper.toGetOrderResponse(order);
    }

    @Override
    public List<GetOrderSummaryResponse> getAllOrders() {

        List<Order> order = orderRepository.findAll();
        return orderMapper.toSummary(order);
    }
}
