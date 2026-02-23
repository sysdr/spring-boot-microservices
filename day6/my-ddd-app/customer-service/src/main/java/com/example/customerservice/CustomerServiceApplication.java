package com.example.customerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class CustomerServiceApplication {

    private final Map<String, Customer> customers = new HashMap<>();

    public CustomerServiceApplication() {
        customers.put("C101", new Customer("C101", "Alice Smith", "alice@example.com"));
        customers.put("C102", new Customer("C102", "Bob Johnson", "bob@example.com"));
    }

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> info = new HashMap<>();
        info.put("service", "Customer Service");
        info.put("version", "1.0.0");
        info.put("endpoints", Map.of(
            "GET /customers/{customerId}", "Get customer by ID",
            "GET /actuator/health", "Health check",
            "GET /actuator/prometheus", "Prometheus metrics"
        ));
        return info;
    }

    @GetMapping("/customers/{customerId}")
    public Customer getCustomer(@PathVariable String customerId) {
        System.out.println("CustomerService: Received request for customer ID: " + customerId);
        Customer customer = customers.get(customerId);
        if (customer == null) {
            System.out.println("CustomerService: Customer not found for ID: " + customerId);
            // In a real system, this would be a 404 or specific error DTO
        } else {
             System.out.println("CustomerService: Found customer: " + customer.name());
        }
        return customer;
    }
}

record Customer(String id, String name, String email) {}
