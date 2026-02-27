package com.petshop.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/pay/{orderId}")
    public String pay(@PathVariable Long orderId) {

        // call Order Service to mark order as PAID
        restTemplate.put(
                "http://localhost:8082/api/orders/"
                        + orderId
                        + "/status?newStatus=PAID",
                null
        );

        return "Payment successful for order " + orderId;
    }
}