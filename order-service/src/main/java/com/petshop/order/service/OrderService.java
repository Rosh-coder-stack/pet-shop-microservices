package com.petshop.order.service;

import com.petshop.order.model.Order;
import com.petshop.order.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.petshop.order.model.OrderStatus;
@Service
public class OrderService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @CircuitBreaker(name = "productService", fallbackMethod = "productFallback")
    public Order placeOrder(Order order) {

        Object product = restTemplate.getForObject(
                "http://PRODUCT-SERVICE/api/products/" + order.getProductId(),
                Object.class
        );

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        return orderRepository.save(order);
    }

    // fallback method
    public Order productFallback(Order order, Exception ex) {
        System.out.println("Product Service is currently unavailable");
        return null;
    }
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrderStatus(Long id, OrderStatus status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }

}