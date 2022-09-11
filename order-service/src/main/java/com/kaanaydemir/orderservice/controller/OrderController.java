package com.kaanaydemir.orderservice.controller;


import com.kaanaydemir.orderservice.dto.OrderRequest;
import com.kaanaydemir.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder (orderRequest);
        return "Order placed successfully";
    }
}
