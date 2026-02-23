package com.example.orderservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {

    @GetMapping(value = {"/", "/info"})
    public Map<String, Object> root() {
        Map<String, Object> info = new HashMap<>();
        info.put("service", "Order Service");
        info.put("version", "1.0.0");
        info.put("endpoints", Map.of(
            "POST /orders", "Place a new order",
            "GET /orders", "Get all orders",
            "GET /orders/{orderId}", "Get order by ID",
            "GET /actuator/health", "Health check",
            "GET /actuator/prometheus", "Prometheus metrics"
        ));
        return info;
    }
}

