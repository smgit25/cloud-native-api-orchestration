package com.cloudcart.order.controller;

import com.cloudcart.order.dto.request.CreateOrderRequest;
import com.cloudcart.order.dto.request.OrderItemRequest;
import com.cloudcart.order.dto.response.CreateOrderResponse;
import com.cloudcart.order.service.impl.OrderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderServiceImpl orderService;

    @Test
    void createOrder_shouldReturn201AndSetCookie() throws Exception {
        UUID customerId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        CreateOrderRequest request = new CreateOrderRequest(
                customerId,
                List.of(new OrderItemRequest(UUID.randomUUID(), 2))
        );

        CreateOrderResponse serviceResponse = new CreateOrderResponse(orderId, "CREATED");
        when(orderService.createOrder(any(CreateOrderRequest.class))).thenReturn(serviceResponse);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId").value(orderId.toString()))
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(header().string("Set-Cookie", containsString("recentOrder=" + orderId)));
    }

    @Test
    void createOrder_shouldReturn400_whenCustomerIdIsNull() throws Exception {
        CreateOrderRequest request = new CreateOrderRequest(
                null,
                List.of(new OrderItemRequest(UUID.randomUUID(), 2))
        );

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}

