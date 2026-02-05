package com.example.orderservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class OrderController {

    private final String applicationName;
    private final MetricsService metricsService;

    public OrderController(String applicationName, MetricsService metricsService) {
        this.applicationName = applicationName;
        this.metricsService = metricsService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", applicationName);
        response.put("status", "running");
        response.put("message", "Order Service is up and running!");
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", applicationName);
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/status")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", applicationName);
        response.put("status", "operational");
        response.put("version", "0.0.1-SNAPSHOT");
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/dashboard/metrics")
    public ResponseEntity<Map<String, Object>> dashboardMetrics() {
        // Get real metrics
        long totalRequests = metricsService.getTotalRequests();
        double errorRate = metricsService.getErrorRate();
        String uptime = metricsService.getUptimeFormatted();
        
        // Get memory usage
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        double memoryUsagePercent = (usedMemory * 100.0) / runtime.maxMemory();
        
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("uptime", uptime);
        metrics.put("requests", totalRequests);
        metrics.put("responseTime", "45ms"); // Can be enhanced with actual response time tracking
        metrics.put("errorRate", String.format("%.2f%%", errorRate));
        metrics.put("activeConnections", 1); // Can be enhanced with actual connection tracking
        metrics.put("memoryUsage", String.format("%.1f%%", memoryUsagePercent));
        metrics.put("cpuUsage", "12%"); // Can be enhanced with actual CPU tracking
        metrics.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/api/dashboard/info")
    public ResponseEntity<Map<String, Object>> dashboardInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("serviceName", applicationName);
        info.put("version", "0.0.1-SNAPSHOT");
        info.put("environment", "development");
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("springBootVersion", "3.5.0");
        info.put("serverPort", System.getProperty("server.port", "8080"));
        info.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(info);
    }

    @GetMapping("/dashboard")
    public RedirectView dashboard() {
        return new RedirectView("/dashboard.html");
    }

    @GetMapping("/favicon.ico")
    public ResponseEntity<Void> favicon() {
        // Return 204 No Content to prevent 404 errors
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

