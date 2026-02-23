package com.example.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@RestController
@RequestMapping("/orders")
public class OrderServiceApplication {

    private final RestTemplate restTemplate;
    private final String customerServiceBaseUrl;
    private final Map<String, Order> orders = new ConcurrentHashMap<>();

    public OrderServiceApplication() {
        this.restTemplate = new RestTemplate();
        // This URL will be different for Docker vs. local
        this.customerServiceBaseUrl = System.getenv("CUSTOMER_SERVICE_URL") != null ?
                                      System.getenv("CUSTOMER_SERVICE_URL") :
                                      "http://localhost:8081"; // Default for local run
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println("OrderService: Received order request for customer ID: " + orderRequest.customerId());
        
        // 1. Call Customer Service (simulating Anti-Corruption Layer/Gateway)
        String customerUrl = customerServiceBaseUrl + "/customers/" + orderRequest.customerId();
        System.out.println("OrderService: Calling Customer Service at: " + customerUrl);
        Customer customer = null;
        try {
            customer = restTemplate.getForObject(customerUrl, Customer.class);
        } catch (Exception e) {
            System.err.println("OrderService: Failed to call Customer Service: " + e.getMessage());
            return ResponseEntity.status(500).body(new OrderResponse(null, "FAILED", "Customer validation failed."));
        }

        if (customer == null || customer.id() == null) {
            System.out.println("OrderService: Customer not found or invalid: " + orderRequest.customerId());
            return ResponseEntity.badRequest().body(new OrderResponse(null, "FAILED", "Invalid customer ID."));
        }

        System.out.println("OrderService: Customer validated: " + customer.name());

        // 2. Process Order (Order Bounded Context's logic)
        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 8);
        Order newOrder = new Order(orderId, orderRequest.customerId(), customer.name(), orderRequest.items(), "PENDING");
        orders.put(orderId, newOrder);
        System.out.println("OrderService: Order created: " + orderId);

        return ResponseEntity.ok(new OrderResponse(orderId, "SUCCESS", "Order placed successfully."));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable String orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(new ArrayList<>(orders.values()));
    }
}

record OrderRequest(String customerId, List<String> items) {}
record OrderResponse(String orderId, String status, String message) {}
record Customer(String id, String name, String email) {} // Simplified Customer representation from external BC
record Order(String id, String customerId, String customerName, List<String> items, String status) {}
