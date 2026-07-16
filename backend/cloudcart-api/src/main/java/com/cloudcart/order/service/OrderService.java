package com.cloudcart.order.service;

import com.cloudcart.order.dto.request.CreateOrderRequest;
import com.cloudcart.order.dto.response.CreateOrderResponse;
import com.cloudcart.order.dto.response.GetOrderResponse;
import com.cloudcart.order.dto.response.GetOrderSummaryResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {

     CreateOrderResponse createOrder(CreateOrderRequest request);

     GetOrderResponse getOrder(UUID id);

     List<GetOrderSummaryResponse> getAllOrders();
}
