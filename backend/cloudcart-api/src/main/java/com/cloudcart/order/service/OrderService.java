package com.cloudcart.order.service;

import com.cloudcart.order.dto.request.CreateOrderRequest;
import com.cloudcart.order.dto.response.CreateOrderResponse;

public interface OrderService {

     CreateOrderResponse createOrder(CreateOrderRequest request);
}
