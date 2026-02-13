package com.example.cdsdemoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class CdsdemoserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdsdemoserviceApplication.class, args);
    }

    @GetMapping("/")
    public Map<String, Object> root() {
        Map<String, Object> info = new HashMap<>();
        info.put("message", "CDS-optimized Spring Boot Service");
        info.put("status", "UP");
        info.put("endpoints", Map.of(
            "hello", "/hello",
            "health", "/actuator/health",
            "metrics", "/actuator/metrics",
            "prometheus", "/actuator/prometheus",
            "demoMetrics", "/metrics/demo"
        ));
        return info;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from CDS-optimized Spring Boot!";
    }
}
