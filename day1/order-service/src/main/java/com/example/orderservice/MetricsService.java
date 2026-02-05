package com.example.orderservice;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class MetricsService {
    
    private final AtomicLong totalRequests = new AtomicLong(0);
    private final AtomicLong errorCount = new AtomicLong(0);
    private long startTime = System.currentTimeMillis();
    
    public void incrementRequest() {
        totalRequests.incrementAndGet();
    }
    
    public void incrementError() {
        errorCount.incrementAndGet();
    }
    
    public long getTotalRequests() {
        return totalRequests.get();
    }
    
    public long getErrorCount() {
        return errorCount.get();
    }
    
    public double getErrorRate() {
        long total = totalRequests.get();
        if (total == 0) return 0.0;
        return (errorCount.get() * 100.0) / total;
    }
    
    public long getUptime() {
        return System.currentTimeMillis() - startTime;
    }
    
    public String getUptimeFormatted() {
        long uptimeMs = getUptime();
        long seconds = uptimeMs / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        
        if (days > 0) {
            return String.format("%dd %dh", days, hours % 24);
        } else if (hours > 0) {
            return String.format("%dh %dm", hours, minutes % 60);
        } else if (minutes > 0) {
            return String.format("%dm %ds", minutes, seconds % 60);
        } else {
            return String.format("%ds", seconds);
        }
    }
}

