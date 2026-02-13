package com.example.cdsdemoservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MetricsController {

    @GetMapping("/metrics/demo")
    public Map<String, Object> getDemoMetrics() {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("timestamp", LocalDateTime.now().toString());
        metrics.put("status", "UP");
        metrics.put("uptime", System.currentTimeMillis());
        metrics.put("memory", getMemoryMetrics());
        return metrics;
    }

    private Map<String, Object> getMemoryMetrics() {
        Runtime runtime = Runtime.getRuntime();
        Map<String, Object> memory = new HashMap<>();
        memory.put("total", runtime.totalMemory());
        memory.put("free", runtime.freeMemory());
        memory.put("used", runtime.totalMemory() - runtime.freeMemory());
        memory.put("max", runtime.maxMemory());
        return memory;
    }
}
