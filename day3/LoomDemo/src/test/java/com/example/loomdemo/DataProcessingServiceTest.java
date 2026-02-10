package com.example.loomdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataProcessingServiceTest {

    @Autowired
    private DataProcessingService dataProcessingService;

    @Test
    void testProcessData() {
        String result = dataProcessingService.processData("TestData");
        assertNotNull(result);
        assertTrue(result.contains("Processed: TestData"));
        assertTrue(result.contains("Virtual Thread"));
    }
}
