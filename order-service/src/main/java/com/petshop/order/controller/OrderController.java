package com.petshop.order.controller;

import com.petshop.order.model.Order;
import com.petshop.order.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderController(OrderRepository orderRepository,RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        // call Product Service
        Object product = restTemplate.getForObject(
                "http://localhost:8081/api/products/" + order.getProductId(),
                Object.class
        );

        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        return orderRepository.save(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
