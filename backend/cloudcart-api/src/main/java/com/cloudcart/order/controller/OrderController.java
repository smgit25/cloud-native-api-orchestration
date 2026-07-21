package com.cloudcart.order.controller;

import com.cloudcart.order.dto.request.CreateOrderRequest;
import com.cloudcart.order.dto.response.CreateOrderResponse;
import com.cloudcart.order.dto.response.GetOrderResponse;
import com.cloudcart.order.dto.response.GetOrderSummaryResponse;
import com.cloudcart.order.entity.Order;
import com.cloudcart.order.service.OrderService;
import com.cloudcart.order.service.impl.OrderServiceImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    private static final Logger log =
            LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest request){

        System.out.println(request);

        CreateOrderResponse response = orderService.createOrder(request);

        ResponseCookie cookie = ResponseCookie.from(
                        "recentOrder",
                        response.orderId().toString())
                .httpOnly(true)
                .secure(false)      // true when using HTTPS
                .path("/")
                .maxAge(3600)
                .sameSite("Strict")
                .build();

        return ResponseEntity
                .created(null)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderbyId(@PathVariable UUID orderId) {

        log.info("Retrieving orders for id:"+orderId);
        try {
            ResponseCookie cookie = ResponseCookie.from("orderId", orderId.toString())
                    .httpOnly(true)
                    .secure(false)      // true in production with HTTPS
                    .path("/")
                    .maxAge(Duration.ofHours(1))
                    .sameSite("Lax")
                    .build();

            GetOrderResponse response = orderService.getOrder(orderId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured");

        }
    }

    @GetMapping("/fetchOrders")
    public ResponseEntity<List<GetOrderSummaryResponse>> getAllOrders(){

        try{
            return ResponseEntity.ok(
                    orderService.getAllOrders()
            );
        } catch (Exception e){
                throw e;
        }

        }


}
