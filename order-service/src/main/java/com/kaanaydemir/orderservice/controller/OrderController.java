package com.kaanaydemir.orderservice.controller;


import com.kaanaydemir.orderservice.dto.OrderRequest;
import com.kaanaydemir.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
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
    /*@CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")*/
    public String placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder (orderRequest);
        return "Order placed successfully";
    }

    public String fallbackMethod(OrderRequest orderRequest, Exception e) {
        return "fallbackMethod - Order failed";
    }
}
