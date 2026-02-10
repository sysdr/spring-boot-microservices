package com.example.loomdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DataProcessingService {

    private static final Logger log = LoggerFactory.getLogger(DataProcessingService.class);

    public String processData(String input) {
        log.info("Processing data for input: {} on thread: {}", input, Thread.currentThread().getName());
        try {
            // Simulate a slow external API call or database operation (I/O bound)
            TimeUnit.SECONDS.sleep(2); // This will park the virtual thread
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Data processing interrupted for input: {}", input, e);
            return "Error: Interrupted";
        }
        log.info("Finished processing data for input: {} on thread: {}", input, Thread.currentThread().getName());
        return "Processed: " + input + " (via Virtual Thread)";
    }
}
