package com.example.hello_native_app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot Native Image!";
    }

    @GetMapping("/api/status")
    public Map<String, Object> getStatus() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        status.put("application", "Spring Boot Native App");
        status.put("version", "1.0.0");
        return status;
    }

    @GetMapping("/api/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalRequests", 1250);
        stats.put("activeUsers", 42);
        stats.put("uptime", "99.9%");
        stats.put("responseTime", "12ms");
        return stats;
    }

    @GetMapping("/api/info")
    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "Hello Native App");
        info.put("description", "Spring Boot Native Image Demo Application");
        info.put("framework", "Spring Boot 4.0.3");
        info.put("javaVersion", "17");
        info.put("nativeImage", true);
        info.put("buildTool", "GraalVM Native Image");
        return info;
    }
}
