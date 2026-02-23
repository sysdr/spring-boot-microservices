package com.example.customerservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"server.port=0"})
class CustomerServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
