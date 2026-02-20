package com.example.hello_native_app;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class DashboardController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String dashboard() {
        try {
            Resource resource = new ClassPathResource("static/index.html");
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "<html><body><h1>Error loading dashboard</h1><p>" + e.getMessage() + "</p></body></html>";
        }
    }
}

