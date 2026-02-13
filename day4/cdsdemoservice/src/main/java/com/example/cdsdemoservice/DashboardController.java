package com.example.cdsdemoservice;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class DashboardController {

    @GetMapping(value = "/dashboard", produces = MediaType.TEXT_HTML_VALUE)
    public String dashboard() throws IOException {
        Resource resource = new ClassPathResource("templates/dashboard.html");
        return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    }
}

