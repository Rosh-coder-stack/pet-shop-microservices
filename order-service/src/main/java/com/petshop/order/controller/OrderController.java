package com.petshop.order.controller;

import com.petshop.order.model.Order;
import com.petshop.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        Order savedOrder = orderService.placeOrder(order);

        if (savedOrder == null) {
            return ResponseEntity
                    .status(503)
                    .body("Product service is currently unavailable. Please try later.");
        }

        return ResponseEntity.ok(savedOrder);
    }
}