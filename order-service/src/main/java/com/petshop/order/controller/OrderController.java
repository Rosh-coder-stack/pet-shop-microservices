package com.petshop.order.controller;

import com.petshop.order.model.Order;
import com.petshop.order.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.petshop.order.model.OrderStatus;
import java.time.LocalDateTime;



@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // CREATE ORDER
    @PostMapping
    public Order createOrder(@RequestBody Order order,
                             @RequestHeader("X-USER-NAME") String username) {

        order.setCreatedBy(username);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        return orderService.createOrder(order);
    }

}

// GET ALL ORDERS (ADMIN
