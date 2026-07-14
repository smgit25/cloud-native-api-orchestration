package com.cloudcart.order.service.impl;

import com.cloudcart.order.dto.request.CreateOrderRequest;
import com.cloudcart.order.dto.request.OrderItemRequest;
import com.cloudcart.order.dto.response.CreateOrderResponse;
import com.cloudcart.order.entity.Order;
import com.cloudcart.order.entity.enums.OrderStatus;
import com.cloudcart.order.mapper.OrderMapper;
import com.cloudcart.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Captor
    private ArgumentCaptor<Order> orderCaptor;

    @Test
    void createOrder_shouldSaveMappedEntityAndReturnMappedResponse() {
        UUID customerId = UUID.randomUUID();
        CreateOrderRequest request = new CreateOrderRequest(
                customerId,
                List.of(new OrderItemRequest(UUID.randomUUID(), 1))
        );

        Order mappedOrder = new Order();
        mappedOrder.setOrderId(UUID.randomUUID());
        mappedOrder.setCustomerId(customerId);
        mappedOrder.setStatus(OrderStatus.CREATED);

        Order savedOrder = new Order();
        savedOrder.setOrderId(mappedOrder.getOrderId());
        savedOrder.setCustomerId(customerId);
        savedOrder.setStatus(OrderStatus.CREATED);

        CreateOrderResponse expectedResponse = new CreateOrderResponse(savedOrder.getOrderId(), OrderStatus.CREATED.name());

        when(orderMapper.toEntity(request)).thenReturn(mappedOrder);
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);
        // return the response for both mapped and saved instances to avoid strict-arg mismatches
        lenient().when(orderMapper.toResponse(any(Order.class))).thenReturn(expectedResponse);






        CreateOrderResponse response = orderService.createOrder(request);

        assertThat(response).isEqualTo(expectedResponse);

        verify(orderMapper).toEntity(request);
        verify(orderRepository).save(orderCaptor.capture());




        assertThat(orderCaptor.getValue()).isSameAs(mappedOrder);
    }
}

