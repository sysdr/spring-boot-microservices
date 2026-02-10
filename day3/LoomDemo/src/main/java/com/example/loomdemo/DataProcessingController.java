package com.example.loomdemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataProcessingController {

    private final DataProcessingService dataProcessingService;

    public DataProcessingController(DataProcessingService dataProcessingService) {
        this.dataProcessingService = dataProcessingService;
    }

    @GetMapping("/api/process")
    public String process(@RequestParam String data) {
        return dataProcessingService.processData(data);
    }
}
