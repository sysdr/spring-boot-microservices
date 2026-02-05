package com.example.orderservice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    private MetricsService metricsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String path = request.getRequestURI();
        
        // Skip static resources, favicon, and dashboard HTML
        if (path.endsWith(".html") || path.endsWith(".ico") || path.endsWith(".css") || 
            path.endsWith(".js") || path.endsWith(".png") || path.endsWith(".jpg") || 
            path.endsWith(".svg") || path.equals("/dashboard")) {
            return true;
        }
        
        // Track all API and service endpoint requests
        metricsService.incrementRequest();
        
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            metricsService.incrementError();
        }
    }
}

