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
    private boolean isValidTransition(OrderStatus current, OrderStatus next) {

        switch (current) {

            case PENDING:
                return next == OrderStatus.PAID
                        || next == OrderStatus.CANCELLED;

            case PAID:
                return next == OrderStatus.SHIPPED;

            case SHIPPED:
            case CANCELLED:
                return false;

            default:
                return false;
        }
    }
    @PutMapping("/{id}/status")
    public Order updateStatus(@PathVariable Long id,
                              @RequestParam OrderStatus newStatus) {

        Order order = orderService.getOrderById(id);

        OrderStatus currentStatus = order.getStatus();

        // validation check

        order.setStatus(newStatus);
        return orderService.createOrder(order);
    }
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}


